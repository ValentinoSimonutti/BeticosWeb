package com.beticos.futbolapp.controller.admin;

import com.beticos.futbolapp.model.Torneo;
import com.beticos.futbolapp.model.enums.EstadoTorneo;
import com.beticos.futbolapp.service.EquipoService;
import com.beticos.futbolapp.service.EquipoTorneoService;
import com.beticos.futbolapp.service.TorneoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/torneos")
public class TorneoAdminController {

    private final TorneoService torneoService;
    private final EquipoService equipoService;
    private final EquipoTorneoService equipoTorneoService;

    public TorneoAdminController(TorneoService torneoService,EquipoService equipoService,EquipoTorneoService equipoTorneoService) {
        this.equipoService = equipoService;
        this.torneoService = torneoService;
        this.equipoTorneoService = equipoTorneoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("torneos", torneoService.listarTorneos());
        return "admin/torneos/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("torneo", new Torneo());
        model.addAttribute("estados", EstadoTorneo.values());
        return "admin/torneos/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute Torneo torneo) {
        torneoService.crearTorneo(
                torneo.getNombre(),
                torneo.getFechaInicio(),
                torneo.getFechaFin(),
                torneo.getEstado()
        );

        return "redirect:/admin/torneos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("torneo", torneoService.buscarTorneoPorId(id));
        model.addAttribute("estados", EstadoTorneo.values());
        return "admin/torneos/actualizar_datos";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id, @ModelAttribute Torneo torneo) {
        torneoService.actualizarTorneo(
                id,
                torneo.getNombre(),
                torneo.getFechaInicio(),
                torneo.getFechaFin(),
                torneo.getEstado()
        );

        return "redirect:/admin/torneos";
    }

    @PostMapping("/{id}/borrar")
    public String borrar(@PathVariable Long id) {
        torneoService.borrarTorneo(id);
        return "redirect:/admin/torneos";
    }

    @GetMapping("/{id}/equipos")
    public String administrarEquipos(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "torneo",
                torneoService.buscarTorneoPorId(id));

        model.addAttribute(
                "equiposDisponibles",
                equipoService.listarEquipos());

        model.addAttribute(
                "equiposInscriptos",
                equipoTorneoService.listarEquiposDeTorneo(id));

        return "admin/torneos/equipos";
    }

    @PostMapping("/{torneoId}/equipos")
    public String agregarEquipo(
            @PathVariable Long torneoId,
            @RequestParam Long equipoId) {

        equipoTorneoService.agregarEquipoATorneo(
                equipoId,
                torneoId);

        return "redirect:/admin/torneos/" + torneoId + "/equipos";
    }
    
    @PostMapping("/{torneoId}/equipos/{equipoId}/borrar")
    public String quitarEquipo(
            @PathVariable Long torneoId,
            @PathVariable Long equipoId) {

        equipoTorneoService.quitarEquipoDeTorneo(
                equipoId,
                torneoId);

        return "redirect:/admin/torneos/" + torneoId + "/equipos";
    }
}

package com.beticos.futbolapp.controller.admin;

import com.beticos.futbolapp.model.Torneo;
import com.beticos.futbolapp.model.enums.EstadoTorneo;
import com.beticos.futbolapp.service.FutbolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/torneos")
public class TorneoAdminController {

    private final FutbolService futbolService;

    public TorneoAdminController(FutbolService futbolService) {
        this.futbolService = futbolService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("torneos", futbolService.listarTorneos());
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
        futbolService.crearTorneo(
                torneo.getNombre(),
                torneo.getFechaInicio(),
                torneo.getFechaFin(),
                torneo.getEstado()
        );

        return "redirect:/admin/torneos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("torneo", futbolService.buscarTorneoPorId(id));
        model.addAttribute("estados", EstadoTorneo.values());
        return "admin/torneos/actualizar_datos";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id, @ModelAttribute Torneo torneo) {
        futbolService.actualizarTorneo(
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
        futbolService.borrarTorneo(id);
        return "redirect:/admin/torneos";
    }
}

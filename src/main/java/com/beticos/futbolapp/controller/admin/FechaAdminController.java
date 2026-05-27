package com.beticos.futbolapp.controller.admin;

import com.beticos.futbolapp.model.Fecha;
import com.beticos.futbolapp.service.FechaService;
import com.beticos.futbolapp.service.TorneoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/torneos/{torneoId}/fechas")
public class FechaAdminController {

    private final FechaService fechaService;
    private final TorneoService torneoService;

    public FechaAdminController(FechaService fechaService, TorneoService torneoService) {
        this.fechaService = fechaService;
        this.torneoService = torneoService;
    }

    @GetMapping
    public String listar(@PathVariable Long torneoId, Model model) {
        model.addAttribute("torneo", torneoService.buscarTorneoPorId(torneoId));
        model.addAttribute("fechas", fechaService.listarFechasPorTorneo(torneoId));
        return "admin/fechas/listado";
    }

    @GetMapping("/nueva")
    public String nueva(@PathVariable Long torneoId, Model model) {
        model.addAttribute("torneo", torneoService.buscarTorneoPorId(torneoId));
        model.addAttribute("fecha", new Fecha());
        return "admin/fechas/formulario";
    }

    @PostMapping
    public String guardar(@PathVariable Long torneoId, @ModelAttribute Fecha fecha) {
        fechaService.crearFecha(torneoId, fecha.getDia(), fecha.getNumero());
        return "redirect:/admin/torneos/" + torneoId + "/fechas";
    }

    @GetMapping("/{fechaId}/editar")
    public String editar(@PathVariable Long torneoId, @PathVariable Long fechaId, Model model) {
        model.addAttribute("torneo", torneoService.buscarTorneoPorId(torneoId));
        model.addAttribute("fecha", fechaService.buscarFechaPorId(fechaId));
        return "admin/fechas/actualizar_datos";
    }

    @PostMapping("/{fechaId}/editar")
    public String actualizar(@PathVariable Long torneoId,
                             @PathVariable Long fechaId,
                             @ModelAttribute Fecha fecha) {
        fechaService.actualizarFecha(fechaId, fecha.getDia(), fecha.getNumero());
        return "redirect:/admin/torneos/" + torneoId + "/fechas";
    }

    @PostMapping("/{fechaId}/borrar")
    public String borrar(@PathVariable Long torneoId, @PathVariable Long fechaId) {
        fechaService.borrarFecha(fechaId);
        return "redirect:/admin/torneos/" + torneoId + "/fechas";
    }
}

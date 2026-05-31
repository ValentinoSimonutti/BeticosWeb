package com.beticos.futbolapp.controller.admin;

import com.beticos.futbolapp.model.Partido;
import com.beticos.futbolapp.service.EquipoTorneoService;
import com.beticos.futbolapp.service.FechaService;
import com.beticos.futbolapp.service.PartidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/fechas")
public class PartidoAdminController {

    private final PartidoService partidoService;
    private final FechaService fechaService;
    private final EquipoTorneoService equipoTorneoService;

    public PartidoAdminController(
            PartidoService partidoService,
            FechaService fechaService,
            EquipoTorneoService equipoTorneoService) {

        this.partidoService = partidoService;
        this.fechaService = fechaService;
        this.equipoTorneoService = equipoTorneoService;
    }

    @GetMapping("/{fechaId}/partidos")
    public String listarPartidos(
            @PathVariable Long fechaId,
            Model model) {

        var fecha = fechaService.buscarFechaPorId(fechaId);

        model.addAttribute(
                "fecha",
                fecha);

        model.addAttribute(
                "partidos",
                partidoService.listarPartidosPorFecha(fechaId));

        model.addAttribute(
                "equiposTorneo",
                equipoTorneoService.listarEquiposDeTorneo(
                        fecha.getTorneo().getId()));

        return "admin/partidos/listado";
    }

    @PostMapping("/{fechaId}/partidos")
    public String crearPartido(
            @PathVariable Long fechaId,

            @RequestParam Long equipoLocalId,

            @RequestParam Long equipoVisitanteId,

            @RequestParam Integer cancha,

            @RequestParam(required = false)
            Integer golesLocal,

            @RequestParam(required = false)
            Integer golesVisitante) {

        Partido partido =
                partidoService.crearPartido(
                        fechaId,
                        equipoLocalId,
                        equipoVisitanteId,
                        cancha);

        if (golesLocal != null &&
                golesVisitante != null) {

            partidoService.cargarResultado(
                    partido.getId(),
                    golesLocal,
                    golesVisitante);
        }

        return "redirect:/admin/fechas/" +
                fechaId +
                "/partidos";
    }

    @PostMapping("/{fechaId}/partidos/{partidoId}/borrar")
    public String borrarPartido(
            @PathVariable Long fechaId,
            @PathVariable Long partidoId) {

        partidoService.borrarPartido(partidoId);

        return "redirect:/admin/fechas/" + fechaId + "/partidos";
    }

    @GetMapping("/{fechaId}/partidos/{partidoId}/editar")
    public String editarResultado(
            @PathVariable Long fechaId,
            @PathVariable Long partidoId,
            Model model) {

        model.addAttribute(
                "fecha",
                fechaService.buscarFechaPorId(fechaId));

        model.addAttribute(
                "partido",
                partidoService.buscarPartidoPorId(partidoId));

        return "admin/partidos/editar_resultado";
    }

    @PostMapping("/{fechaId}/partidos/{partidoId}/editar")
    public String actualizarResultado(
            @PathVariable Long fechaId,
            @PathVariable Long partidoId,
            @RequestParam Integer golesLocal,
            @RequestParam Integer golesVisitante) {

        partidoService.cargarResultado(
                partidoId,
                golesLocal,
                golesVisitante);

        return "redirect:/admin/fechas/" + fechaId + "/partidos";
    }



}
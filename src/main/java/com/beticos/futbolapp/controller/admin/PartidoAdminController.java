package com.beticos.futbolapp.controller.admin;

import com.beticos.futbolapp.model.Partido;
import com.beticos.futbolapp.model.enums.TipoEventoPartido;
import com.beticos.futbolapp.service.EquipoTorneoService;
import com.beticos.futbolapp.service.EventoPartidoService;
import com.beticos.futbolapp.service.FechaService;
import com.beticos.futbolapp.service.JugadorService;
import com.beticos.futbolapp.service.PartidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/fechas")
public class PartidoAdminController {

    private final PartidoService partidoService;
    private final FechaService fechaService;
    private final EquipoTorneoService equipoTorneoService;
    private final JugadorService jugadorService;
    private final EventoPartidoService eventoPartidoService;

    public PartidoAdminController(
            PartidoService partidoService,
            FechaService fechaService,
            EquipoTorneoService equipoTorneoService,
            JugadorService jugadorService,
            EventoPartidoService eventoPartidoService) {

        this.partidoService = partidoService;
        this.fechaService = fechaService;
        this.equipoTorneoService = equipoTorneoService;
        this.jugadorService = jugadorService;
        this.eventoPartidoService = eventoPartidoService;
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

        agregarDatosEventosAlModelo(model);

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
            Integer golesVisitante,

            @RequestParam(
                    required = false,
                    name = "eventoJugadorId")
            List<String> eventoJugadorIds,

            @RequestParam(
                    required = false,
                    name = "eventoTipo")
            List<String> eventoTipos) {

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

        eventoPartidoService.reemplazarEventosDePartido(
                partido.getId(),
                eventoJugadorIds,
                eventoTipos);

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

        Partido partido = partidoService.buscarPartidoPorId(partidoId);

        model.addAttribute(
                "partido",
                partido);

        model.addAttribute(
                "eventosPartido",
                eventoPartidoService.findAllEventoPartido(partido));

        agregarDatosEventosAlModelo(model);

        return "admin/partidos/editar_resultado";
    }

    @PostMapping("/{fechaId}/partidos/{partidoId}/editar")
    public String actualizarResultado(
            @PathVariable Long fechaId,
            @PathVariable Long partidoId,
            @RequestParam Integer golesLocal,
            @RequestParam Integer golesVisitante,

            @RequestParam(
                    required = false,
                    name = "eventoJugadorId")
            List<String> eventoJugadorIds,

            @RequestParam(
                    required = false,
                    name = "eventoTipo")
            List<String> eventoTipos) {

        partidoService.cargarResultado(
                partidoId,
                golesLocal,
                golesVisitante);

        eventoPartidoService.reemplazarEventosDePartido(
                partidoId,
                eventoJugadorIds,
                eventoTipos);

        return "redirect:/admin/fechas/" + fechaId + "/partidos";
    }

    private void agregarDatosEventosAlModelo(Model model) {

        model.addAttribute(
                "jugadores",
                jugadorService.listarJugadores());

        model.addAttribute(
                "tiposEvento",
                TipoEventoPartido.values());
    }


}

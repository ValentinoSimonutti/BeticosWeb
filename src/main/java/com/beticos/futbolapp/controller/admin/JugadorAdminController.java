package com.beticos.futbolapp.controller.admin;

import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.model.Jugador;
import com.beticos.futbolapp.repository.EquipoRepository;
import com.beticos.futbolapp.service.EquipoService;
import com.beticos.futbolapp.service.JugadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/jugadores")
public class JugadorAdminController {

    private final JugadorService jugadorService;
    private final EquipoService equipoService;

    public JugadorAdminController(JugadorService jugadorService, EquipoService equipoService) {
        this.jugadorService = jugadorService;
        this.equipoService = equipoService;
    }

    @GetMapping
    public String listar (Model model){
        model.addAttribute("jugadores", jugadorService.listarJugadores());
        return "admin/jugadores/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo (Model model){
        List<Equipo> equipos = equipoService.listarEquipos();
        model.addAttribute("jugador", new Jugador());
        model.addAttribute("equipos", equipos);
        return "admin/jugadores/formulario";
    }

    @PostMapping
    public String guardar(
            @ModelAttribute Jugador jugador,
            @RequestParam Long equipoId) {

        Equipo equipo = equipoService.buscarEquipoPorId(equipoId);

        jugadorService.crearJugador(
                jugador.getNombreCompleto(),
                jugador.getDescripcion(),
                jugador.getPosicion(),
                equipo
        );

        return "redirect:/admin/jugadores";
    }

    @GetMapping("/{id}/editar")
    public String editar (@PathVariable Long id, Model model){
        model.addAttribute("jugador", jugadorService.buscarJugadorPorId(id));
        return "admin/jugadores/actualizar_datos";
    }

    @PostMapping("/{id}/editar")
    public String actualizar (@PathVariable Long id, @ModelAttribute Jugador jugador){
        jugadorService.actualizarJugador(id, jugador.getNombreCompleto(),jugador.getDescripcion(), jugador.getPosicion() );
        return "redirect:/admin/jugadores";
    }

    @PostMapping("/{id}/borrar")
    public String borrar(@PathVariable Long id) {
        jugadorService.borrarJugador(id);
        return "redirect:/admin/jugadores";
    }

}

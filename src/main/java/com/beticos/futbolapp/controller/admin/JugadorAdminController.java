package com.beticos.futbolapp.controller.admin;

import com.beticos.futbolapp.model.Jugador;
import com.beticos.futbolapp.service.FutbolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/jugadores")
public class JugadorAdminController {

    private final FutbolService futbolService;

    public JugadorAdminController(FutbolService futbolService) {
        this.futbolService = futbolService;
    }

    @GetMapping
    public String listar (Model model){
        model.addAttribute("jugadores", futbolService.listarJugadores());
        return "admin/jugadores/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo (Model model){
        model.addAttribute("jugador", new Jugador());
        return "admin/jugadores/formulario";
    }

    @PostMapping
    public String guardar (@ModelAttribute Jugador jugador){
        futbolService.crearJugador(jugador.getNombreCompleto(), jugador.getDescripcion(), jugador.getPosicion());
        return "redirect:/admin/jugadores";
    }

    @GetMapping("/{id}/editar")
    public String editar (@PathVariable Long id, Model model){
        model.addAttribute("jugador", futbolService.buscarJugadorPorId(id));
        return "admin/jugadores/actualizar_datos";
    }

    @PostMapping("/{id}/editar")
    public String actualizar (@PathVariable Long id, @ModelAttribute Jugador jugador){
        futbolService.actualizarJugador(id, jugador.getNombreCompleto(),jugador.getDescripcion(), jugador.getPosicion() );
        return "redirect:/admin/jugadores";
    }

    @PostMapping("/{id}/borrar")
    public String borrar(@PathVariable Long id) {
        futbolService.borrarJugador(id);
        return "redirect:/admin/jugadores";
    }

}

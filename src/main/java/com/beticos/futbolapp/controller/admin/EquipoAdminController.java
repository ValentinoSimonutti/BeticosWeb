package com.beticos.futbolapp.controller.admin;

import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.service.FutbolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/equipos")
public class EquipoAdminController {

    private final FutbolService futbolService;

    public EquipoAdminController(FutbolService futbolService) {
        this.futbolService = futbolService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipos", futbolService.findAll());
        return "listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("equipo", new Equipo());
        return "formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute Equipo equipo) {
        futbolService.save(equipo.getNombre());
        return "redirect:/admin/equipos";
    }
}

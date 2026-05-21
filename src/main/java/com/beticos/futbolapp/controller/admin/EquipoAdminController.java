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
        model.addAttribute("equipos", futbolService.listarEquipos());
        return "admin/equipos/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("equipo", new Equipo());
        return "admin/equipos/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute Equipo equipo) {
        futbolService.crearEquipo(equipo.getNombre());
        return "redirect:/admin/equipos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("equipo", futbolService.buscarEquipoPorId(id));
        return "admin/equipos/actualizar_datos";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id, @ModelAttribute Equipo equipo) {
        futbolService.actualizarEquipo(id, equipo.getNombre());
        return "redirect:/admin/equipos";
    }

    @PostMapping("/{id}/borrar")
    public String borrar(@PathVariable Long id) {
        this.futbolService.borrarEquipo(id);
        return "redirect:/admin/equipos";
    }
}

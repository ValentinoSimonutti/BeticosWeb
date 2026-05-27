package com.beticos.futbolapp.controller.api;

import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.service.EquipoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoApiController {

    private final EquipoService equipoService;

    public EquipoApiController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public List<Equipo> listarEquipos() {
        return equipoService.listarEquipos();
    }

    @GetMapping("/{nombre}")
    public Equipo buscarPorNombre(@PathVariable String nombre) {
        return equipoService.buscarEquipoPorNombre(nombre);
    }

}
package com.beticos.futbolapp.controller.api;

import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.service.FutbolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoApiController {

    private final FutbolService futbolService;

    public EquipoApiController(FutbolService futbolService) {
        this.futbolService = futbolService;
    }

    @GetMapping
    public List<Equipo> listarEquipos() {
        return futbolService.findAll();
    }

    @GetMapping("/{nombre}")
    public Equipo buscarPorNombre(@PathVariable String nombre) {
        return futbolService.findByNombre(nombre);
    }

}
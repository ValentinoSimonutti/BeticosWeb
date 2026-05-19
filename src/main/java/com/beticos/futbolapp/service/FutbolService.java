package com.beticos.futbolapp.service;

import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.repository.EquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FutbolService {

    private final EquipoRepository equipoRepository;

    public FutbolService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    @Transactional
    public Equipo save(String nombre) {
        return equipoRepository.save(new Equipo(nombre));
    }

    @Transactional(readOnly = true)
    public Equipo findByNombre(String nombre) {
        return equipoRepository.findByNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

}

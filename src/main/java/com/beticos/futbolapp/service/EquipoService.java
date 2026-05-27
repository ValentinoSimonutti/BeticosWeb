package com.beticos.futbolapp.service;

import com.beticos.futbolapp.exception.ResourceNotFoundException;
import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.repository.EquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    @Transactional
    public Equipo crearEquipo(String nombre) {
        Equipo equipo = new Equipo(nombre);
        return equipoRepository.save(equipo);
    }

    @Transactional
    public Equipo actualizarEquipo(Long id, String nombre) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado"));

        equipo.setNombre(nombre);
        return equipoRepository.save(equipo);
    }

    @Transactional
    public void borrarEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipo no encontrado");
        }
        equipoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Equipo> listarEquipos() {
        return equipoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Equipo buscarEquipoPorId(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado"));
    }

    @Transactional(readOnly = true)
    public Equipo buscarEquipoPorNombre(String nombre) {
        return equipoRepository.findByNombre(nombre);
    }
}

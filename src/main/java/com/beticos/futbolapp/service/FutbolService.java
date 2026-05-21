package com.beticos.futbolapp.service;

import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.repository.EquipoRepository;
import com.beticos.futbolapp.repository.JugadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class FutbolService {

    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;

    public FutbolService(EquipoRepository equipoRepository,
                         JugadorRepository jugadorRepository) {
        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional
    public Equipo crearEquipo(String nombre) {
        Equipo equipo = new Equipo(nombre);
        return equipoRepository.save(equipo);
    }

    @Transactional
    public Equipo actualizarEquipo(Long id, String nombre) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        equipo.setNombre(nombre);
        return equipoRepository.save(equipo);
    }

    @Transactional
    public void borrarEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado");
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
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    @Transactional(readOnly = true)
    public Equipo buscarEquipoPorNombre(String nombre) {
        return equipoRepository.findByNombre(nombre);
    }




}

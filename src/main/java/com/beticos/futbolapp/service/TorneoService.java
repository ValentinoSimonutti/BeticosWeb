package com.beticos.futbolapp.service;

import com.beticos.futbolapp.exception.ResourceNotFoundException;
import com.beticos.futbolapp.model.Torneo;
import com.beticos.futbolapp.model.enums.EstadoTorneo;
import com.beticos.futbolapp.repository.TorneoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TorneoService {

    private final TorneoRepository torneoRepository;

    public TorneoService(TorneoRepository torneoRepository) {
        this.torneoRepository = torneoRepository;
    }

    @Transactional
    public Torneo crearTorneo(String nombre, LocalDate fechaInicio, LocalDate fechaFin, EstadoTorneo estado) {
        Torneo torneo = new Torneo(nombre, fechaInicio, fechaFin, estado);
        return torneoRepository.save(torneo);
    }

    @Transactional(readOnly = true)
    public List<Torneo> listarTorneos() {
        return torneoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Torneo buscarTorneoPorId(Long id) {
        return torneoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Torneo no encontrado"));
    }


    @Transactional
    public Torneo actualizarTorneo(Long id,
                                   String nombre,
                                   LocalDate fechaInicio,
                                   LocalDate fechaFin,
                                   EstadoTorneo estado) {

        Torneo torneo = buscarTorneoPorId(id);

        torneo.setNombre(nombre);
        torneo.setFechaInicio(fechaInicio);
        torneo.setFechaFin(fechaFin);
        torneo.setEstado(estado);

        return torneoRepository.save(torneo);
    }

    @Transactional
    public void borrarTorneo(Long id) {

        if (!torneoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Torneo no encontrado");
        }

        torneoRepository.deleteById(id);
    }

}

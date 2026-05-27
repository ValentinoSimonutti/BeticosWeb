package com.beticos.futbolapp.service;

import com.beticos.futbolapp.exception.BadRequestException;
import com.beticos.futbolapp.exception.ResourceNotFoundException;
import com.beticos.futbolapp.model.Fecha;
import com.beticos.futbolapp.model.Torneo;
import com.beticos.futbolapp.repository.FechaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FechaService {

   private final FechaRepository fechaRepository;
   private final TorneoService torneoService;

    public FechaService (FechaRepository fechaRepository, TorneoService torneoService) {
        this.fechaRepository = fechaRepository;
        this.torneoService = torneoService;
    }

    @Transactional
    public Fecha crearFecha(Long torneoId, LocalDate dia, Integer numero) {
        Torneo torneo = torneoService.buscarTorneoPorId(torneoId);

        if (fechaRepository.existsByTorneoAndNumero(torneo, numero)) {
            throw new BadRequestException("Ya existe esa fecha para el torneo");
        }
        Fecha fecha = new Fecha(numero,dia, torneo);
        return fechaRepository.save(fecha);
    }

    @Transactional(readOnly = true)
    public List<Fecha> listarFechas() {
        return fechaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Fecha buscarFechaPorId(Long id) {
        return fechaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fecha no encontrada"));
    }

    @Transactional
    public void borrarFecha(Long id) {
        if (!fechaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fecha no encontrada");
        }

        fechaRepository.deleteById(id);
    }

    @Transactional
    public Fecha actualizarFecha(Long id, LocalDate dia, Integer numero) {
        Fecha fecha = buscarFechaPorId(id);

        fechaRepository.findByTorneoAndNumero(fecha.getTorneo(), numero)
                .filter(fechaExistente -> !fechaExistente.getId().equals(id))
                .ifPresent(fechaExistente -> {
                    throw new BadRequestException("Ya existe esa fecha para el torneo");
                });

        fecha.setDia(dia);
        fecha.setNumero(numero);

        return fechaRepository.save(fecha);
    }

    @Transactional(readOnly = true)
    public List<Fecha> listarFechasPorTorneo(Long torneoId) {
        Torneo torneo = torneoService.buscarTorneoPorId(torneoId);
        return fechaRepository.findByTorneoOrderByNumeroAsc(torneo);
    }

}

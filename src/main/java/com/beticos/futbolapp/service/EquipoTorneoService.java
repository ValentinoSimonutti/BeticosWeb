package com.beticos.futbolapp.service;

import com.beticos.futbolapp.exception.BadRequestException;
import com.beticos.futbolapp.exception.ResourceNotFoundException;
import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.model.EquipoTorneo;
import com.beticos.futbolapp.model.Torneo;
import com.beticos.futbolapp.repository.EquipoRepository;
import com.beticos.futbolapp.repository.EquipoTorneoRepository;
import com.beticos.futbolapp.repository.TorneoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipoTorneoService {

    private final EquipoTorneoRepository equipoTorneoRepository;
    private final EquipoRepository equipoRepository;
    private final TorneoRepository torneoRepository;

    public EquipoTorneoService(EquipoRepository equipoRepository,
                               TorneoRepository torneoRepository,
                               EquipoTorneoRepository equipoTorneoRepository) {
        this.equipoRepository = equipoRepository;
        this.torneoRepository = torneoRepository;
        this.equipoTorneoRepository = equipoTorneoRepository;
    }

    @Transactional
    public EquipoTorneo agregarEquipoATorneo(Long equipoId, Long torneoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado"));

        Torneo torneo = torneoRepository.findById(torneoId)
                .orElseThrow(() -> new ResourceNotFoundException("Torneo no encontrado"));

        if (equipoTorneoRepository.existsByEquipoAndTorneo(equipo, torneo)) {
            throw new BadRequestException("El equipo ya está agregado al torneo");
        }

        EquipoTorneo equipoTorneo = new EquipoTorneo(equipo, torneo);
        return equipoTorneoRepository.save(equipoTorneo);
    }
}

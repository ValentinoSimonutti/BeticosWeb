package com.beticos.futbolapp.service;

import com.beticos.futbolapp.exception.BadRequestException;
import com.beticos.futbolapp.exception.ResourceNotFoundException;
import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.model.Fecha;
import com.beticos.futbolapp.model.Partido;
import com.beticos.futbolapp.model.Torneo;
import com.beticos.futbolapp.repository.EquipoTorneoRepository;
import com.beticos.futbolapp.repository.EventoPartidoRepository;
import com.beticos.futbolapp.repository.PartidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final FechaService fechaService;
    private final EquipoService equipoService;
    private final EquipoTorneoRepository equipoTorneoRepository;
    private final EventoPartidoRepository eventoPartidoRepository;

    public PartidoService(
            PartidoRepository partidoRepository,
            FechaService fechaService,
            EquipoService equipoService,
            EquipoTorneoRepository equipoTorneoRepository,
            EventoPartidoRepository eventoPartidoRepository) {

        this.partidoRepository = partidoRepository;
        this.fechaService = fechaService;
        this.equipoService = equipoService;
        this.equipoTorneoRepository = equipoTorneoRepository;
        this.eventoPartidoRepository = eventoPartidoRepository;
    }

    @Transactional
    public Partido crearPartido(
            Long fechaId,
            Long equipoLocalId,
            Long equipoVisitanteId,
            Integer cancha) {

        Fecha fecha = fechaService.buscarFechaPorId(fechaId);

        Equipo equipoLocal =
                equipoService.buscarEquipoPorId(equipoLocalId);

        Equipo equipoVisitante =
                equipoService.buscarEquipoPorId(equipoVisitanteId);

        Torneo torneo = fecha.getTorneo();

        if (equipoLocal.getId().equals(equipoVisitante.getId())) {
            throw new BadRequestException(
                    "El equipo local no puede ser igual al visitante");
        }

        if (!equipoTorneoRepository.existsByEquipoAndTorneo(
                equipoLocal,
                torneo)) {

            throw new BadRequestException(
                    "El equipo local no participa del torneo");
        }

        if (!equipoTorneoRepository.existsByEquipoAndTorneo(
                equipoVisitante,
                torneo)) {

            throw new BadRequestException(
                    "El equipo visitante no participa del torneo");
        }

        boolean localYaJuega =
                partidoRepository.existsByFechaAndEquipoLocal(
                        fecha,
                        equipoLocal)
                        ||
                        partidoRepository.existsByFechaAndEquipoVisitante(
                                fecha,
                                equipoLocal);

        if (localYaJuega) {
            throw new BadRequestException(
                    "El equipo local ya tiene un partido en esta fecha");
        }

        boolean visitanteYaJuega =
                partidoRepository.existsByFechaAndEquipoLocal(
                        fecha,
                        equipoVisitante)
                        ||
                        partidoRepository.existsByFechaAndEquipoVisitante(
                                fecha,
                                equipoVisitante);

        if (visitanteYaJuega) {
            throw new BadRequestException(
                    "El equipo visitante ya tiene un partido en esta fecha");
        }

        Partido partido = new Partido(
                fecha,
                cancha,
                torneo,
                equipoLocal,
                equipoVisitante,
                0,
                0
        );

        return partidoRepository.save(partido);
    }

    @Transactional(readOnly = true)
    public Partido buscarPartidoPorId(Long id) {

        return partidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Partido no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Partido> listarPartidosPorFecha(Long fechaId) {

        Fecha fecha = fechaService.buscarFechaPorId(fechaId);

        return partidoRepository.findByFechaOrderByIdAsc(fecha);
    }

    @Transactional
    public Partido cargarResultado(
            Long partidoId,
            Integer golesLocal,
            Integer golesVisitante) {

        if (golesLocal < 0 || golesVisitante < 0) {
            throw new BadRequestException(
                    "Los goles no pueden ser negativos");
        }

        Partido partido = buscarPartidoPorId(partidoId);

        partido.setGolesLocal(golesLocal);
        partido.setGolesVisitante(golesVisitante);

        return partidoRepository.save(partido);
    }

    @Transactional
    public void borrarPartido(Long partidoId) {

        Partido partido = buscarPartidoPorId(partidoId);

        eventoPartidoRepository.deleteByPartido(partido);
        partidoRepository.delete(partido);
    }
}

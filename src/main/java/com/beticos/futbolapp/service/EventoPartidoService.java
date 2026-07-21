package com.beticos.futbolapp.service;

import com.beticos.futbolapp.dto.EstadisticasJugadorDTO;
import com.beticos.futbolapp.exception.BadRequestException;
import com.beticos.futbolapp.exception.ResourceNotFoundException;
import com.beticos.futbolapp.model.*;
import com.beticos.futbolapp.model.enums.TipoEventoPartido;
import com.beticos.futbolapp.repository.EventoPartidoRepository;
import com.beticos.futbolapp.repository.JugadorRepository;
import com.beticos.futbolapp.repository.PartidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class EventoPartidoService {

    private final EventoPartidoRepository eventoPartidoRepository;
    private final PartidoRepository partidoRepository;
    private final JugadorRepository jugadorRepository;

    public EventoPartidoService(EventoPartidoRepository eventoPartidoRepository, PartidoRepository partidoRepository, JugadorRepository jugadorRepository) {
        this.eventoPartidoRepository = eventoPartidoRepository;
        this.partidoRepository = partidoRepository;
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional
    public EventoPartido crearEventoPartido(Partido partido, Jugador jugador, TipoEventoPartido tipo) {
        if (!partidoRepository.existsById(partido.getId())) {
            throw new ResourceNotFoundException("Partido no encontrado");
        }

        if (!jugadorRepository.existsById(jugador.getId())) {
            throw new ResourceNotFoundException("Jugador no encontrado");
        }

        EventoPartido e = new EventoPartido(partido, jugador, tipo);
        return this.eventoPartidoRepository.save(e);
    }

    @Transactional
    public void deleteEventoPartido(Long id) {
        this.eventoPartidoRepository.deleteById(id);
    }

    @Transactional
    public void deleteEventosDePartido(Partido partido) {
        this.eventoPartidoRepository.deleteByPartido(partido);
    }

    @Transactional(readOnly = true)
    public List<EventoPartido> findAllEventoPartido(Partido partido) {
        return this.eventoPartidoRepository.findByPartido(partido);
    }

    @Transactional(readOnly = true)
    public List<EventoPartido> findAllJugador(Jugador jugador) {
        return this.eventoPartidoRepository.findByJugador(jugador);
    }

    @Transactional(readOnly = true)
    public long contarEventosDeJugadorEnTorneo (Torneo torneo, Jugador jugador, TipoEventoPartido tipo) {
        return this.eventoPartidoRepository.countByPartido_TorneoAndJugadorAndTipo(torneo, jugador, tipo);
    }

    @Transactional(readOnly = true)
    public long contarEventosDeJugador(Jugador jugador, TipoEventoPartido tipo) {
        return this.eventoPartidoRepository.countByJugadorAndTipo(jugador, tipo);
    }

    @Transactional(readOnly = true)
    public EstadisticasJugadorDTO calcularEstadisticasJugador(Jugador jugador) {
        return new EstadisticasJugadorDTO(
                jugador,
                contarEventosDeJugador(jugador, TipoEventoPartido.GOL),
                contarEventosDeJugador(jugador, TipoEventoPartido.ASISTENCIA),
                contarEventosDeJugador(jugador, TipoEventoPartido.AMARILLA),
                contarEventosDeJugador(jugador, TipoEventoPartido.ROJA)
        );
    }

    @Transactional(readOnly = true)
    public EstadisticasJugadorDTO calcularEstadisticasJugadorEnTorneo(Torneo torneo, Jugador jugador) {
        return new EstadisticasJugadorDTO(
                jugador,
                contarEventosDeJugadorEnTorneo(torneo, jugador, TipoEventoPartido.GOL),
                contarEventosDeJugadorEnTorneo(torneo, jugador, TipoEventoPartido.ASISTENCIA),
                contarEventosDeJugadorEnTorneo(torneo, jugador, TipoEventoPartido.AMARILLA),
                contarEventosDeJugadorEnTorneo(torneo, jugador, TipoEventoPartido.ROJA)
        );
    }

    @Transactional(readOnly = true)
    public List<EstadisticasJugadorDTO> calcularEstadisticasJugadoresEnTorneo(Torneo torneo, List<Jugador> jugadores) {
        return jugadores.stream()
                .map(jugador -> calcularEstadisticasJugadorEnTorneo(torneo, jugador))
                .sorted(Comparator
                        .comparingLong(EstadisticasJugadorDTO::getGoles).reversed()
                        .thenComparing(Comparator.comparingLong(EstadisticasJugadorDTO::getAsistencias).reversed())
                        .thenComparing(Comparator.comparingLong(EstadisticasJugadorDTO::getAmarillas).reversed())
                        .thenComparing(Comparator.comparingLong(EstadisticasJugadorDTO::getRojas).reversed())
                        .thenComparing(estadisticas -> estadisticas.getJugador().getNombreCompleto()))
                .toList();
    }

    @Transactional
    public void reemplazarEventosDePartido(
            Long partidoId,
            List<String> jugadorIds,
            List<String> tipos) {

        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Partido no encontrado"));

        deleteEventosDePartido(partido);

        List<String> ids = jugadorIds != null
                ? jugadorIds
                : Collections.emptyList();

        List<String> tiposEvento = tipos != null
                ? tipos
                : Collections.emptyList();

        int cantidadFilas = Math.max(ids.size(), tiposEvento.size());

        for (int i = 0; i < cantidadFilas; i++) {

            String jugadorIdTexto = obtenerValor(ids, i);
            String tipoTexto = obtenerValor(tiposEvento, i);

            boolean filaVacia =
                    jugadorIdTexto.isBlank() &&
                            tipoTexto.isBlank();

            if (filaVacia) {
                continue;
            }

            if (jugadorIdTexto.isBlank() || tipoTexto.isBlank()) {
                throw new BadRequestException(
                        "Cada evento debe tener jugador y tipo");
            }

            Jugador jugador = jugadorRepository.findById(
                            convertirJugadorId(jugadorIdTexto))
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Jugador no encontrado"));

            validarJugadorDelPartido(partido, jugador);

            TipoEventoPartido tipo = convertirTipoEvento(tipoTexto);

            crearEventoPartido(partido, jugador, tipo);
        }
    }

    private String obtenerValor(List<String> valores, int indice) {

        if (indice >= valores.size() || valores.get(indice) == null) {
            return "";
        }

        return valores.get(indice).trim();
    }

    private Long convertirJugadorId(String jugadorIdTexto) {

        try {
            return Long.valueOf(jugadorIdTexto);
        } catch (NumberFormatException ex) {
            throw new BadRequestException(
                    "Jugador inválido para el evento");
        }
    }

    private TipoEventoPartido convertirTipoEvento(String tipoTexto) {

        try {
            return TipoEventoPartido.valueOf(tipoTexto);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(
                    "Tipo de evento inválido");
        }
    }

    private void validarJugadorDelPartido(
            Partido partido,
            Jugador jugador) {

        if (jugador.getEquipo() == null) {
            throw new BadRequestException(
                    "El jugador no tiene equipo asignado");
        }

        Long equipoJugadorId = jugador.getEquipo().getId();

        boolean perteneceAlPartido =
                partido.getEquipoLocal().getId().equals(equipoJugadorId)
                        ||
                        partido.getEquipoVisitante().getId().equals(equipoJugadorId);

        if (!perteneceAlPartido) {
            throw new BadRequestException(
                    "El jugador no pertenece a ninguno de los equipos del partido");
        }
    }
}

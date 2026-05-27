package com.beticos.futbolapp.service;

import com.beticos.futbolapp.exception.ResourceNotFoundException;
import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.model.EquipoTorneo;
import com.beticos.futbolapp.model.Jugador;
import com.beticos.futbolapp.model.Torneo;
import com.beticos.futbolapp.model.enums.EstadoTorneo;
import com.beticos.futbolapp.model.enums.PosicionJugador;
import com.beticos.futbolapp.repository.EquipoRepository;
import com.beticos.futbolapp.repository.EquipoTorneoRepository;
import com.beticos.futbolapp.repository.JugadorRepository;
import com.beticos.futbolapp.repository.TorneoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Service
public class FutbolService {

    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final TorneoRepository torneoRepository;
    private final EquipoTorneoRepository equipoTorneoRepository;

    public FutbolService(EquipoRepository equipoRepository,
                         JugadorRepository jugadorRepository,
                         TorneoRepository torneoRepository,
                         EquipoTorneoRepository equipoTorneoRepository) {
        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
        this.torneoRepository = torneoRepository;
        this.equipoTorneoRepository = equipoTorneoRepository;
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

    @Transactional(readOnly = true)
    public List<Jugador> listarJugadores() {
        return jugadorRepository.findAll();
    }

    @Transactional
    public Jugador crearJugador(String nombre, String descripcion, PosicionJugador posicion) {
        Jugador jugador = new Jugador(nombre, descripcion, posicion);
                return jugadorRepository.save(jugador);
    }

    @Transactional(readOnly = true)
    public Jugador buscarJugadorPorId(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado"));
    }

    @Transactional
    public Jugador actualizarJugador(Long id, String nombreCompleto, String descripcion, PosicionJugador posicion) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado"));

        jugador.setNombreCompleto(nombreCompleto);
        jugador.setDescripcion(descripcion);
        jugador.setPosicion(posicion);

        return jugadorRepository.save(jugador);
    }

    @Transactional
    public void borrarJugador(Long id) {
        if (!jugadorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Jugador no encontrado");
        }
        jugadorRepository.deleteById(id);
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
    public EquipoTorneo agregarEquipoATorneo(Long equipoId, Long torneoId) {
        Equipo equipo = buscarEquipoPorId(equipoId);
        Torneo torneo = buscarTorneoPorId(torneoId);

        EquipoTorneo equipoTorneo = new EquipoTorneo(equipo, torneo);
        return equipoTorneoRepository.save(equipoTorneo);
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

package com.beticos.futbolapp.service;

import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.model.Jugador;
import com.beticos.futbolapp.model.enums.PosicionJugador;
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
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    @Transactional
    public Jugador actualizarJugador(Long id, String nombreCompleto, String descripcion, PosicionJugador posicion) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        jugador.setNombreCompleto(nombreCompleto);
        jugador.setDescripcion(descripcion);
        jugador.setPosicion(posicion);

        return jugadorRepository.save(jugador);
    }

    @Transactional
    public void borrarJugador(Long id) {
        if (!jugadorRepository.existsById(id)) {
            throw new RuntimeException("Jugador no encontrado");
        }
        jugadorRepository.deleteById(id);
    }
}

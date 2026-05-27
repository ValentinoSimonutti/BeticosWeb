package com.beticos.futbolapp.service;

import com.beticos.futbolapp.exception.ResourceNotFoundException;
import com.beticos.futbolapp.model.Jugador;
import com.beticos.futbolapp.model.enums.PosicionJugador;
import com.beticos.futbolapp.repository.JugadorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JugadorService {


    private JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
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

}

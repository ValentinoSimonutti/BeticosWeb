package com.beticos.futbolapp.model;

import com.beticos.futbolapp.model.enums.TipoEventoPartido;
import jakarta.persistence.*;

@Entity
public class EventoPartido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Partido partido;

    @ManyToOne(optional = false)
    private Jugador jugador;

    @Enumerated(EnumType.STRING)
    private TipoEventoPartido tipo;

    public EventoPartido() {
    }

    public EventoPartido(Partido partido, Jugador jugador, TipoEventoPartido tipo) {
        this.partido = partido;
        this.jugador = jugador;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public TipoEventoPartido getTipo() {
        return tipo;
    }

    public void setTipo(TipoEventoPartido tipo) {
        this.tipo = tipo;
    }
}

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

    private Integer cantidad;

    public EventoPartido() {
    }

    public EventoPartido(Partido partido, Jugador jugador, TipoEventoPartido tipo, Integer cantidad) {
        this.partido = partido;
        this.jugador = jugador;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    // getters y setters
}

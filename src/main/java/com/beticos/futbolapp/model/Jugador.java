package com.beticos.futbolapp.model;

import com.beticos.futbolapp.model.enums.PosicionJugador;
import jakarta.persistence.*;

@Entity
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String nombreCompleto;

    @Column(length = 300)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private PosicionJugador posicion;

    private boolean activo;

    @ManyToOne(optional = false)
    private Equipo equipo;

    public Jugador() {
    }

    public Jugador(String nombreCompleto,
                   String descripcion,
                   PosicionJugador posicion,
                   Equipo equipo) {

        this.nombreCompleto = nombreCompleto;
        this.descripcion = descripcion;
        this.posicion = posicion;
        this.equipo = equipo;
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public PosicionJugador getPosicion() {
        return posicion;
    }

    public void setPosicion(PosicionJugador posicion) {
        this.posicion = posicion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
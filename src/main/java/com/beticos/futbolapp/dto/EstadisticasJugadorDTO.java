package com.beticos.futbolapp.dto;

import com.beticos.futbolapp.model.Jugador;

public class EstadisticasJugadorDTO {
    private Jugador jugador;
    private long goles;
    private long asistencias;
    private long amarillas;
    private long rojas;

    public EstadisticasJugadorDTO(Jugador jugador, long goles, long asistencias, long amarillas, long rojas) {
        this.jugador = jugador;
        this.goles = goles;
        this.asistencias = asistencias;
        this.amarillas = amarillas;
        this.rojas = rojas;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public long getGoles() {
        return goles;
    }

    public void setGoles(long goles) {
        this.goles = goles;
    }

    public long getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(long asistencias) {
        this.asistencias = asistencias;
    }

    public long getRojas() {
        return rojas;
    }

    public void setRojas(long rojas) {
        this.rojas = rojas;
    }

    public long getAmarillas() {
        return amarillas;
    }

    public void setAmarillas(long amarillas) {
        this.amarillas = amarillas;
    }
}

package com.beticos.futbolapp.repository;

import com.beticos.futbolapp.model.*;
import com.beticos.futbolapp.model.enums.TipoEventoPartido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoPartidoRepository extends JpaRepository<EventoPartido, Long> {

    List<EventoPartido> findByPartido(Partido partido);

    List<EventoPartido> findByPartido_Torneo(Torneo torneo);

    List<EventoPartido> findByPartidoAndJugador(Partido partido, Jugador jugador);

    List<EventoPartido> findByJugador(Jugador jugador);

    List<EventoPartido> findByPartido_TorneoAndJugador(Torneo torneo, Jugador jugador);

    long countByPartido_TorneoAndJugadorAndTipo(Torneo torneo, Jugador jugador, TipoEventoPartido tipo);

    void deleteByPartido(Partido partido);

}

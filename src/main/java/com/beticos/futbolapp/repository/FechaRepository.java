package com.beticos.futbolapp.repository;

import com.beticos.futbolapp.model.Fecha;
import com.beticos.futbolapp.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FechaRepository extends JpaRepository<Fecha, Long> {

    List<Fecha> findByTorneoOrderByNumeroAsc(Torneo torneo);

    Optional<Fecha> findByTorneoAndNumero(Torneo torneo, Integer numero);

    boolean existsByTorneoAndNumero(Torneo torneo, Integer numero);

}

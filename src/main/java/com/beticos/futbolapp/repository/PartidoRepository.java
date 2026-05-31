package com.beticos.futbolapp.repository;

import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.model.Fecha;
import com.beticos.futbolapp.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

    List<Partido> findByFechaOrderByIdAsc(Fecha fecha);

    boolean existsByFechaAndEquipoLocalAndEquipoVisitante(
            Fecha fecha,
            Equipo equipoLocal,
            Equipo equipoVisitante);
    
    boolean existsByFechaAndEquipoLocal(
            Fecha fecha,
            Equipo equipo);

    boolean existsByFechaAndEquipoVisitante(
            Fecha fecha,
            Equipo equipo);
}
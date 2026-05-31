package com.beticos.futbolapp.repository;

import com.beticos.futbolapp.model.Equipo;
import com.beticos.futbolapp.model.EquipoTorneo;
import com.beticos.futbolapp.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipoTorneoRepository extends JpaRepository<EquipoTorneo, Long> {

    List<EquipoTorneo> findByTorneoOrderByPuntosDescDiferenciaGolDescGolesFavorDesc(Torneo torneo);

    boolean existsByEquipoAndTorneo(Equipo equipo, Torneo torneo);

    void deleteByEquipoAndTorneo(Equipo equipo, Torneo torneo);
}

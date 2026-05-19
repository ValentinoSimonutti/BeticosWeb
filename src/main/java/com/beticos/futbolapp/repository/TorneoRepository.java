package com.beticos.futbolapp.repository;

import com.beticos.futbolapp.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
}

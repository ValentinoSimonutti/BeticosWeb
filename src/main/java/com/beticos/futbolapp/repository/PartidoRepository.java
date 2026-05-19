package com.beticos.futbolapp.repository;

import com.beticos.futbolapp.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
}

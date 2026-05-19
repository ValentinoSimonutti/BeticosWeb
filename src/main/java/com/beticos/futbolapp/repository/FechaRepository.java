package com.beticos.futbolapp.repository;

import com.beticos.futbolapp.model.Fecha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FechaRepository extends JpaRepository<Fecha, Long> {
}

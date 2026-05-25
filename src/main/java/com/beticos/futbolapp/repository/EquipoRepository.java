package com.beticos.futbolapp.repository;

import com.beticos.futbolapp.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Equipo findByNombre(String nombre);
}
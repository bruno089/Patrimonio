package com.gabru.Patrimonio.data.repositories;

import com.gabru.Patrimonio.data.entities.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConceptoRepository extends JpaRepository<Concepto,Integer> {
    Optional<Concepto> findByNombre(String nombre);
    List<Concepto> findByNombreContaining(String nombre);
}

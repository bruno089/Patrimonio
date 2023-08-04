package com.gabru.Patrimonio.repositories;

import com.gabru.Patrimonio.entities.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConceptoRepository extends JpaRepository<Concepto,Integer> {
    Optional<Concepto> findByNombre(String nombre);
    List<Concepto> findByNombreContaining(String nombre);
}

package com.gabru.Patrimonio.repositories;

import com.gabru.Patrimonio.entities.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptoRepository extends JpaRepository<Concepto,Integer> {
}

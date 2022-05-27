package com.gabru.Patrimonio.repositories;

import com.gabru.Patrimonio.entities.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConceptoRepository extends JpaRepository<Concepto,Integer> {
/*
    //Primer opcion menos recomendada
    @Query(value = "SELECT * FROM CONCEPTO c WHERE nIdConcepto = ?1 and c.activo = true",nativeQuery = true)
    Concepto cachuPorId(int id);

    //Segunda opcion
    Concepto findByIdAndActivo(int id, boolean activo);

    //JPQL*/

    Optional<Concepto> findByNombre(String nombre);
}

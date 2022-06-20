package com.gabru.Patrimonio.repositories;

import com.gabru.Patrimonio.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento,Integer> {
    List<Movimiento> findAllByAltaBetween(LocalDateTime fechaInicial, LocalDateTime fechaFinal); //Nombrado.Implementa Spring por detras
    @Query("SELECT m FROM Movimiento AS m WHERE m.alta BETWEEN :fechaInicial and :fechaFinal" )
    List<Movimiento> findAllByAltaBetween_Jpql (LocalDateTime fechaInicial, LocalDateTime fechaFinal); //JPQL
    @Query(value = "select * from movimiento t where t.fecha between :fechaInicial and :fechaFinal ",nativeQuery = true )
    List<Movimiento> findAllByAltaBetween_Sql_Nativo (LocalDateTime fechaInicial, LocalDateTime fechaFinal); //Nativo

}

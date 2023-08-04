package com.gabru.Patrimonio.data.repositories;

import com.gabru.Patrimonio.api.dtos.TransactionDto;
import com.gabru.Patrimonio.data.entities.Transaction;
import com.gabru.Patrimonio.data.entities.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovimientoRepository extends JpaRepository<Transaction,Integer> {
    List<Transaction> findAllByAltaBetween( LocalDateTime fechaInicial, LocalDateTime fechaFinal); //Nombrado.Implementa Spring por detras
    @Query("SELECT m FROM Transaction AS m WHERE m.alta BETWEEN :fechaInicial and :fechaFinal" )
    List<Transaction> findAllByAltaBetween_Jpql ( LocalDateTime fechaInicial, LocalDateTime fechaFinal); //JPQL
    @Query(value = "select * from movimiento t where t.fecha between :fechaInicial and :fechaFinal ",nativeQuery = true )
    List<Transaction> findAllByAltaBetween_Sql_Nativo ( LocalDateTime fechaInicial, LocalDateTime fechaFinal); //Nativo
    List<TransactionDto> findAllByFechaBetween( LocalDate fechaInicial, LocalDate fechaFinal);
    List<TransactionDto> findAllByFechaBetweenOrderByFecha( LocalDate fechaInicial, LocalDate fechaFinal);
    List<TransactionDto> findAllByFechaBetweenAndUsuarioOrderByFecha( LocalDate fechaInicial, LocalDate fechaFinal, Usuario usuario );
    Optional<Transaction> findById( int id);
    Optional<Transaction> findByIdAndUsuario( Integer id, Usuario usuario);

    List<Transaction> findAllByUsuario( Usuario  usuario, Sort sort );
}

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

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Query("SELECT t FROM Transaction AS t WHERE t.dateCreation BETWEEN :fechaInicial and :fechaFinal" )
    List<Transaction> findAllByAltaBetween_Jpql ( LocalDateTime fechaInicial, LocalDateTime fechaFinal);
    List<TransactionDto> findAllByDateBetweenOrderByDate ( LocalDate init , LocalDate end);
    Optional<Transaction> findById ( int id);
    Optional<Transaction> findByIdAndUser ( Integer id, Usuario user);
    List<Transaction> findAllByUser ( Usuario  user, Sort sort );

}

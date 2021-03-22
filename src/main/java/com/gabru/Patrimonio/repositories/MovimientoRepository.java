package com.gabru.Patrimonio.repositories;

import com.gabru.Patrimonio.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento,Integer> {
}

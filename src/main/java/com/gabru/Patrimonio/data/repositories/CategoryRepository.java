package com.gabru.Patrimonio.data.repositories;

import com.gabru.Patrimonio.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Optional<Category> findByNombre( String nombre);
    List<Category> findByNombreContaining( String nombre);
}

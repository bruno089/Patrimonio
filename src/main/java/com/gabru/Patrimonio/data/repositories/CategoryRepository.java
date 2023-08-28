package com.gabru.Patrimonio.data.repositories;

import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.data.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByNameContainingAndUser ( String nombre, Usuario usuario);
    List<Category> findAllByUser ( Usuario usuario );
    Optional<Category> findByIdAndUser ( Integer id, Usuario usuario);
    Optional<Category> findByNameAndUser ( String nombre, Usuario usuario);
}

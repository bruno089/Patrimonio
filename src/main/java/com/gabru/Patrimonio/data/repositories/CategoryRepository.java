package com.gabru.Patrimonio.data.repositories;

import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.data.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Optional<Category> findByNombre( String nombre);
    List<Category> findByNombreContainingAndUsuario( String nombre,Usuario usuario);
    List<Category> findAllByUsuario( Usuario usuario );
    Optional<Category> findByIdAndUsuario(Integer id,Usuario usuario);
    Optional<Category> findByNombreAndUsuario( String nombre,Usuario usuario);
}

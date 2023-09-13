package com.gabru.Patrimonio.data.repositories;

import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.data.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByNameContainingAndUser ( String nombre, Usuario usuario);
    List<Category> findAllByUser ( Usuario usuario );
    Optional<Category> findByIdAndUser ( Integer id, Usuario usuario);
    @Query("SELECT c FROM Category c WHERE UPPER(TRIM(c.name)) = UPPER(TRIM(:name)) AND c.user = :user")
    Optional<Category> findByNameAndUserCleanedName ( String name, Usuario user);

}

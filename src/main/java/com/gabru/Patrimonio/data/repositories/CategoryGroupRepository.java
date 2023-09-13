package com.gabru.Patrimonio.data.repositories;

import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.data.entities.CategoryGroup;
import com.gabru.Patrimonio.data.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup,Integer> {
    Optional<CategoryGroup> findByNameAndUser ( String name, Usuario usuarioAutenticado );

    Optional<CategoryGroup> findByIdAndUser ( int id, Usuario usuarioAutenticado );

    List<CategoryGroup> findAllByUser ( Usuario usuarioAutenticado );
    @Query("SELECT cg FROM CategoryGroup cg " +
            "WHERE (cg.id = :categoryGroupId AND cg.user = :user)" +
            "OR (TRIM(UPPER(cg.name)) = TRIM(UPPER(:name)) AND cg.user = :user)")
    Optional<CategoryGroup> findByIdAndUserOrNameAndUser ( Integer categoryGroupId,String name, Usuario user );
}

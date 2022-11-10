package com.gabru.Patrimonio.repositories;

import com.gabru.Patrimonio.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByNombreIgnoreCase (String username);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Usuario> findByNombreAndActivo(String username, boolean active);

}

package com.gabru.Patrimonio.data.repositories;

import com.gabru.Patrimonio.data.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByNombreIgnoreCase (String username);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Usuario> findByNombreAndActivo(String username, boolean active);



}

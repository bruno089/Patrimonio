package com.gabru.Patrimonio.repositories;

import com.gabru.Patrimonio.entities.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode,Integer> {
    Optional<ConfirmationCode> findByCode( String confirmationCode);

}

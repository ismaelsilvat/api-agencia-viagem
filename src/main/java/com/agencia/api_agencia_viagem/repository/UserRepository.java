package com.agencia.api_agencia_viagem.repository;

import com.agencia.api_agencia_viagem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

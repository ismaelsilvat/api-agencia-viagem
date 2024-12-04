package com.agencia.api_agencia_viagem.repository;

import com.agencia.api_agencia_viagem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
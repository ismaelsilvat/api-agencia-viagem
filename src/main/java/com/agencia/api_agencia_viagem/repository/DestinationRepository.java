package com.agencia.api_agencia_viagem.repository;

import com.agencia.api_agencia_viagem.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByNameContaining(String name);
    List<Destination> findByLocationContaining(String location);
}

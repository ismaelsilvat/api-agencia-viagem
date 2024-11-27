package com.agencia.api_agencia_viagem.service;

import com.agencia.api_agencia_viagem.model.Destination;
import com.agencia.api_agencia_viagem.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {
    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public Destination save(Destination destination) {
        destination.setAverageGrade(10);
        return destinationRepository.save(destination);
    }

    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }

    public List<Destination> searchByName(String name) {
        return destinationRepository.findByNameContaining(name);
    }

    public List<Destination> searchByLocation(String location) {
        return destinationRepository.findByLocationContaining(location);
    }

    public Optional<Destination> findById(Long id) {
        return destinationRepository.findById(id);
    }

    public Destination rateDestination(Long id, double grade) {
        Destination destination = destinationRepository.findById(id).orElseThrow(() -> new RuntimeException("Destination not found"));
        double averageGrade = (destination.getAverageGrade() + grade) / 2;
        destination.setAverageGrade(Math.round(averageGrade * 100.0) / 100.0);
        return destinationRepository.save(destination);
    }

    public void deleteById(Long id) {
        destinationRepository.deleteById(id);
    }
}

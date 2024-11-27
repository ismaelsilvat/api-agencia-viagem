package com.agencia.api_agencia_viagem.controller;

import com.agencia.api_agencia_viagem.model.Destination;
import com.agencia.api_agencia_viagem.service.DestinationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService DestinationService) {
        this.destinationService = DestinationService;
    }

    @PostMapping
    public ResponseEntity<Destination> save(@RequestBody Destination Destination) {
        return ResponseEntity.ok(destinationService.save(Destination));
    }

    @GetMapping
    public ResponseEntity<List<Destination>> findAll() {
        return ResponseEntity.ok(destinationService.findAll());
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Destination>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(destinationService.searchByName(name));
    }

    @GetMapping("/search/location")
    public ResponseEntity<List<Destination>> searchByLocation(@RequestParam String location) {
        return ResponseEntity.ok(destinationService.searchByLocation(location));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> findById(@PathVariable Long id) {
        return destinationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/rate")
    public ResponseEntity<Destination> rateDestination(@PathVariable Long id, @RequestParam double grade) {
        return ResponseEntity.ok(destinationService.rateDestination(id, grade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        destinationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.naja.pfe1.controller;

import com.naja.pfe1.model.Encadrant;
import com.naja.pfe1.repository.EncadrantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/encadrant")
public class EncadrantController {

    private final EncadrantRepository encadrantRepository;

    public EncadrantController(EncadrantRepository encadrantRepository) {
        this.encadrantRepository = encadrantRepository;
    }

    // Retrieve all encadrants
    @GetMapping("/all")
    public List<Encadrant> getAllEncadrants() {
        return encadrantRepository.findAll();
    }

    // Create an encadrant entity
    @PostMapping("/add")
    public ResponseEntity<Encadrant> createEncadrant(@RequestBody Encadrant encadrant) {
        Encadrant savedEncadrant = encadrantRepository.save(encadrant);
        return ResponseEntity.created(URI.create("/encadrant/" + savedEncadrant.getId())).body(savedEncadrant);
    }

    // Retrieve an encadrant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Encadrant> getEncadrantById(@PathVariable int id) {
        return encadrantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing encadrant
    @PutMapping("/update/{id}")
    public ResponseEntity<Encadrant> updateEncadrant(@PathVariable int id, @RequestBody Encadrant updatedEncadrant) {
        return encadrantRepository.findById(id)
                .map(existingEncadrant -> {
                    updatedEncadrant.setId(existingEncadrant.getId());
                    Encadrant savedEncadrant = encadrantRepository.save(updatedEncadrant);
                    return ResponseEntity.ok(savedEncadrant);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an encadrant by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEncadrant(@PathVariable int id) {
        return encadrantRepository.findById(id)
                .map(existingEncadrant -> {
                    encadrantRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

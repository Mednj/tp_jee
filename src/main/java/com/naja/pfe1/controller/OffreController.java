package com.naja.pfe1.controller;

import com.naja.pfe1.model.Offre;
import com.naja.pfe1.repository.OffreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offre")
public class OffreController {

    private final OffreRepository offreRepository;

    public OffreController(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    // Retrieve all offres
    @GetMapping("/all")
    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    // Create an offre entity
    @PostMapping("/add")
    public ResponseEntity<Offre> createOffre(@RequestBody Offre offre) {
        Offre savedOffre = offreRepository.save(offre);
        return ResponseEntity.created(URI.create("/offre/" + savedOffre.getId())).body(savedOffre);
    }

    // Retrieve an offre by ID
    @GetMapping("/{id}")
    public ResponseEntity<Offre> getOffreById(@PathVariable int id) {
        return offreRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing offre
    @PutMapping("/update/{id}")
    public ResponseEntity<Offre> updateOffre(@PathVariable int id, @RequestBody Offre updatedOffre) {
        return offreRepository.findById(id)
                .map(existingOffre -> {
                    updatedOffre.setId(existingOffre.getId());
                    Offre savedOffre = offreRepository.save(updatedOffre);
                    return ResponseEntity.ok(savedOffre);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an offre by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOffre(@PathVariable int id) {
        return offreRepository.findById(id)
                .map(existingOffre -> {
                    offreRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

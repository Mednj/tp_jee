package com.naja.pfe1.controller;

import com.naja.pfe1.model.Etudiant;
import com.naja.pfe1.model.Offre;
import com.naja.pfe1.repository.EtudiantRepository;
import com.naja.pfe1.repository.OffreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/etudiant")
public class EtudiantController {

    private final EtudiantRepository etudiantRepository;
    private final OffreRepository offreRepository;

    public EtudiantController(EtudiantRepository etudiantRepository, OffreRepository offreRepository) {
        this.etudiantRepository = etudiantRepository;
        this.offreRepository = offreRepository;
    }

    // Retrieve all etudiants
    @GetMapping("/all")
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    // Create an etudiant entity
    @PostMapping("/add")
    public ResponseEntity<Etudiant> createEntity(@RequestBody Etudiant etudiant) {
        Etudiant savedEntity = etudiantRepository.save(etudiant);
        return ResponseEntity.created(URI.create("/etudiant/" + savedEntity.getId())).body(savedEntity);
    }

    // Retrieve an etudiant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable int id) {
        return etudiantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing etudiant
    @PutMapping("/update/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable int id, @RequestBody Etudiant updatedEtudiant) {
        return etudiantRepository.findById(id)
                .map(existingEtudiant -> {
                    updatedEtudiant.setId(existingEtudiant.getId());
                    Etudiant savedEtudiant = etudiantRepository.save(updatedEtudiant);
                    return ResponseEntity.ok(savedEtudiant);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an etudiant by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEtudiant(@PathVariable int id) {
        return etudiantRepository.findById(id)
                .map(existingEtudiant -> {
                    etudiantRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    //Assign an Offre to an Etudiant:
    @PostMapping("/etudiant/{etudiantId}/assign-offre/{offreId}")
    public ResponseEntity<Etudiant> assignOffreToEtudiant(
            @PathVariable int etudiantId,
            @PathVariable int offreId) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(etudiantId);
        Optional<Offre> optionalOffre = offreRepository.findById(offreId);

        if (optionalEtudiant.isPresent() && optionalOffre.isPresent()) {
            Etudiant etudiant = optionalEtudiant.get();
            Offre offre = optionalOffre.get();
            etudiant.getOffres().add(offre);
            etudiantRepository.save(etudiant);
            return ResponseEntity.ok(etudiant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Remove an Offre from an Etudiant:
    @DeleteMapping("/etudiant/{etudiantId}/remove-offre/{offreId}")
    public ResponseEntity<Etudiant> removeOffreFromEtudiant(
            @PathVariable int etudiantId,
            @PathVariable int offreId) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(etudiantId);
        Optional<Offre> optionalOffre = offreRepository.findById(offreId);

        if (optionalEtudiant.isPresent() && optionalOffre.isPresent()) {
            Etudiant etudiant = optionalEtudiant.get();
            Offre offre = optionalOffre.get();
            etudiant.getOffres().remove(offre);
            etudiantRepository.save(etudiant);
            return ResponseEntity.ok(etudiant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


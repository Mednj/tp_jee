package com.naja.pfe1.controller;

import com.naja.pfe1.model.Encadrant;
import com.naja.pfe1.model.Pfe;
import com.naja.pfe1.repository.EncadrantRepository;
import com.naja.pfe1.repository.PfeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pfe")
public class PfeController {

    private final PfeRepository pfeRepository;
    private final EncadrantRepository encadrantRepository;

    public PfeController(PfeRepository pfeRepository, EncadrantRepository encadrantRepository) {
        this.pfeRepository = pfeRepository;
        this.encadrantRepository = encadrantRepository;
    }

    // Retrieve all PFE projects
    @GetMapping("/all")
    public List<Pfe> getAllPfeProjects() {
        return pfeRepository.findAll();
    }

    // Create a PFE project entity
    @PostMapping("/add")
    public ResponseEntity<Pfe> createPfeProject(@RequestBody Pfe pfe) {
        Pfe savedPfeProject = pfeRepository.save(pfe);
        return ResponseEntity.created(URI.create("/pfe/" + savedPfeProject.getRef())).body(savedPfeProject);
    }

    // Retrieve a PFE project by its reference
    @GetMapping("/{ref}")
    public ResponseEntity<Pfe> getPfeProjectByRef(@PathVariable int ref) {
        return pfeRepository.findById(ref)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing PFE project
    @PutMapping("/update/{ref}")
    public ResponseEntity<Pfe> updatePfeProject(@PathVariable int ref, @RequestBody Pfe updatedPfe) {
        return pfeRepository.findById(ref)
                .map(existingPfe -> {
                    updatedPfe.setRef(existingPfe.getRef());
                    Pfe savedPfe = pfeRepository.save(updatedPfe);
                    return ResponseEntity.ok(savedPfe);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a PFE project by its reference
    @DeleteMapping("/delete/{ref}")
    public ResponseEntity<?> deletePfeProject(@PathVariable int ref) {
        return pfeRepository.findById(ref)
                .map(existingPfe -> {
                    pfeRepository.deleteById(ref);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    // Assign an encadrant to a PFE project
    @PostMapping("/pfe/{pfeRef}/assign-encadrant/{encadrantId}")
    public ResponseEntity<Pfe> assignEncadrantToPfe(
            @PathVariable int pfeRef,
            @PathVariable int encadrantId) {
        Optional<Pfe> optionalPfe = pfeRepository.findById(pfeRef);
        Optional<Encadrant> optionalEncadrant = encadrantRepository.findById(encadrantId);

        if (optionalPfe.isPresent() && optionalEncadrant.isPresent()) {
            Pfe pfe = optionalPfe.get();
            Encadrant encadrant = optionalEncadrant.get();
            pfe.setEncadrant(encadrant);
            pfeRepository.save(pfe);
            return ResponseEntity.ok(pfe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Remove an encadrant from a PFE project
    @DeleteMapping("/pfe/{pfeRef}/remove-encadrant")
    public ResponseEntity<Pfe> removeEncadrantFromPfe(@PathVariable int pfeRef) {
        Optional<Pfe> optionalPfe = pfeRepository.findById(pfeRef);

        if (optionalPfe.isPresent()) {
            Pfe pfe = optionalPfe.get();
            pfe.setEncadrant(null);
            pfeRepository.save(pfe);
            return ResponseEntity.ok(pfe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

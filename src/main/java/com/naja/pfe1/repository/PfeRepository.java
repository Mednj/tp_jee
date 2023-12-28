package com.naja.pfe1.repository;

import com.naja.pfe1.model.Pfe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PfeRepository extends JpaRepository<Pfe, Integer> {
}

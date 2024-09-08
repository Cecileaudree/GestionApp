package com.example.projetA.Repository;

import com.example.projetA.Entity.Appareils;
import com.example.projetA.Entity.RapportF1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RapportF1Repository extends JpaRepository<RapportF1,Long> {
    Optional<RapportF1> findByidRapport(Long idRapport);
}

package com.example.projetA.Repository;

import com.example.projetA.Entity.Appareils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface AppareilsRepository extends JpaRepository<Appareils,Long> {

    List<Appareils> findAll(Sort sort);

    Optional<Appareils> findAllByidAppareils(Long idAppareils);

    List<Appareils> findByIstakenTrue();

    List<Appareils> findByIstakenTrueAndUtiliseTrue();

    List<Appareils> findByDateServiceBetween(LocalDate startDate, LocalDate endDate);

   List<Appareils> findByDatefinBefore(LocalDate date);
}

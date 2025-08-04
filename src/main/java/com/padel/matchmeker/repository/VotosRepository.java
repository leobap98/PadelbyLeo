package com.padel.matchmeker.repository;

import com.padel.matchmeker.model.Votos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotosRepository extends JpaRepository<Votos, Long> {
    Votos findByMatchId(Long id);
}

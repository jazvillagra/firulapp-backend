package com.github.firulapp.repository;

import com.github.firulapp.domain.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BreedRepository extends JpaRepository<Breed, Long> {
    List<Breed> findBySpeciesId(Long speciesId);
}

package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entite.Absence;

public interface RepositoryAbsence extends JpaRepository<Absence, Integer> {

}

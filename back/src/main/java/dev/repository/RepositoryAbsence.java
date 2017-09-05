package dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entite.Absence;


public interface RepositoryAbsence extends JpaRepository<Absence, Integer> {

	List<Absence> findByIdEmploye(Integer id);

}

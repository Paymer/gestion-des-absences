package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entite.Collaborateur;

public interface RepositoryCollaborateur extends JpaRepository<Collaborateur, Integer> {

}

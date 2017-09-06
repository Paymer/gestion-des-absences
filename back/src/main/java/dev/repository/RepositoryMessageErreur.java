package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entite.MessageErreur;

public interface RepositoryMessageErreur extends JpaRepository<MessageErreur, Integer> {

}

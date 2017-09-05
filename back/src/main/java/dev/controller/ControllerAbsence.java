package dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entite.Absence;
import dev.repository.RepositoryAbsence;


@RestController
@RequestMapping("/absence")
public class ControllerAbsence {
	@Autowired
	private RepositoryAbsence repoAbsence;

	@GetMapping()
	public List<Absence> listerAbsences() {
		return this.repoAbsence.findAll();
	}
	
	@GetMapping("/{idEmploye}")
	public List<Absence> findAbsenceParId(@PathVariable Integer idEmploye) {
		return this.repoAbsence.findByIdEmploye(idEmploye);
	}
}

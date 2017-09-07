package dev.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.repository.RepositoryAbsence;
import dev.service.ServiceAbsence;

@RestController
@RequestMapping("/absence")
public class ControllerAbsence {

	@Autowired
	private RepositoryAbsence repoAbsence;

	@Autowired
	private ServiceAbsence serAbsence;

	@GetMapping()
	public List<Absence> listerAbsences() {
		return this.repoAbsence.findAll();
	}

	@GetMapping("/{matriculeEmploye}")
	public List<Absence> findAbsenceParMatriculeEmploye(@PathVariable String matriculeEmploye) {
		return this.repoAbsence.findByMatriculeEmploye(matriculeEmploye);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/demande", consumes = "application/json;charset=UTF-8")
	public String ajoutAbsence(@RequestBody Absence newAbsence) {
		newAbsence.setStatut(Statut.INITIALE);

		if (serAbsence.conditions(newAbsence)) {
			repoAbsence.save(newAbsence);
		}

		return "";

	}

	@RequestMapping(method = RequestMethod.PUT, path = "/modification/{id}", consumes = "application/json;charset=UTF-8")
	public String modifyAbsence(@PathVariable int id,  @RequestBody Absence newAbsence) {

		// check si les conditions se sont correctes pour la modifier
		if (serAbsence.conditions(newAbsence)) {
		
			newAbsence.setStatut(Statut.INITIALE);
			// addObject
			repoAbsence.save(newAbsence);
		}
		
		return "";

	}

}
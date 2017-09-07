package dev.controller;


import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
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
import dev.entite.Collaborateur;
import dev.entite.Collaborateur.Grade;
import dev.repository.RepositoryAbsence;
import dev.service.ServiceAbsence;
import dev.service.ServiceCollaborateur;

@RestController
@RequestMapping("/absence")
public class ControllerAbsence {

	@Autowired
	private RepositoryAbsence repoAbsence;

	@Autowired
	private ServiceAbsence serAbsence;
	
	@Autowired
	private ServiceCollaborateur serCollaborateur;

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
		System.out.println(serAbsence.conditions(newAbsence));
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

	@RequestMapping(value = "/validation/{matriculeManager}", method = RequestMethod.GET, produces = "application/json")
	public String findAbsencesEnAttenteDepuisSubalternes(@PathVariable String matriculeManager){
		JSONArray tableauAbsences = new JSONArray();
		serAbsence.getAbsencesPourManager(matriculeManager).forEach(a -> {
			Optional<Collaborateur> optCollab = serCollaborateur.findCollaborateurParMatricule(a.getMatriculeEmploye());
			if(optCollab.isPresent()){
				Collaborateur collab = optCollab.get();
				JSONObject objectAbsence = new JSONObject();
				objectAbsence.put("id", a.getId());
				objectAbsence.put("dateDebut", a.getDateDebut().toString());
				objectAbsence.put("dateFin", a.getDateFin().toString());
				objectAbsence.put("type", a.getType().toString());
				objectAbsence.put("nom", collab.getNom()+" "+collab.getPrenom());
				objectAbsence.put("motif", a.getMotif());
				tableauAbsences.put(objectAbsence);
			}
		});
		return tableauAbsences.toString();
	}

	@RequestMapping(value = "/validation/{matriculeValidateur}/{idAbsence}/{statut}", method = RequestMethod.PATCH, produces = "application/json")
	public String findAbsenceParMatriculeEmploye(@PathVariable String matriculeValidateur, @PathVariable Integer idAbsence, @PathVariable boolean statut){
		JSONObject retour = new JSONObject();
		retour.put("success", false);
		Optional<Collaborateur> validateur = serCollaborateur.findCollaborateurParMatricule(matriculeValidateur);
		if(validateur.isPresent() && validateur.get().getGrade()==Grade.MANAGER){
			boolean valid = serAbsence.validationDemande(validateur.get(), idAbsence, statut);
			retour.put("success", valid);
		}
		return retour.toString();
	}

}
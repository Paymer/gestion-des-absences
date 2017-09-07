package dev.rest;

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.entite.Collaborateur;
import dev.entite.Collaborateur.Grade;
import dev.service.ServiceAbsence;
import dev.service.ServiceCollaborateur;

@RestController
@RequestMapping("/validationAbsence")
public class RestValidationAbsence {

	@Autowired
	private ServiceCollaborateur collabs;

	@Autowired
	private ServiceAbsence absences;

	@RequestMapping(value = "/{matriculeValidateur}/{idAbsence}/{statut}", method = RequestMethod.PATCH, produces = "application/json")
	public String findAbsenceParMatriculeEmploye(@PathVariable String matriculeValidateur, @PathVariable Integer idAbsence, @PathVariable boolean statut) throws JSONException {
		JSONObject retour = new JSONObject();
		retour.put("success", false);
		Optional<Collaborateur> validateur = collabs.findCollaborateurParMatricule(matriculeValidateur);
		if(validateur.isPresent() && validateur.get().getGrade()==Grade.MANAGER){
			boolean valid = absences.validationDemande(validateur.get(), idAbsence, statut);
			retour.put("success", valid);
		}
		return retour.toString();
	}

}
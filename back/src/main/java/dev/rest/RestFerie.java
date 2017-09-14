package dev.rest;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.entite.Absence.TypeAbsence;
import dev.entite.Collaborateur;
import dev.entite.Collaborateur.Grade;
import dev.repository.RepositoryAbsence;
import dev.service.ServiceAbsence;
import dev.service.ServiceCollaborateur;

@RestController
@RequestMapping("/ferie")
public class RestFerie {

	@Autowired
	private ServiceAbsence serviceAbsence;
	@Autowired
	private ServiceCollaborateur serviceCollaborateur;
	@Autowired
	private RepositoryAbsence repoAbsence;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public String getJourFeriesEtRttEmployeur() {
		JSONArray jsonArrayFeries = new JSONArray();
		serviceAbsence.getJoursFeriesEtRttEmployeurs().forEach(currentJour -> {
			JSONObject jsonCurrentFerie = new JSONObject();
			String nomJour = currentJour.getDateDebut().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);
			jsonCurrentFerie.put("id", currentJour.getId()).put("date", currentJour.getDateDebut())
					.put("type", currentJour.getType())
					.put("jour", Character.toUpperCase(nomJour.charAt(0)) + nomJour.substring(1))
					.put("commentaires", currentJour.getMotif());
			jsonArrayFeries.put(jsonCurrentFerie);
		});
		return jsonArrayFeries.toString();
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public String creationFerie(@RequestBody String strFerie) {
		boolean valid = false;
		JSONObject objFerie = new JSONObject(strFerie);
		String matricule = objFerie.getString("matricule");
		Optional<Collaborateur> oc = serviceCollaborateur.findCollaborateurParMatricule(matricule);
		if(oc.isPresent() && oc.get().getGrade()==Grade.ADMINISTRATEUR){
			String strDate = objFerie.getString("date");
			String strType = objFerie.getString("type");
			String commentaires = objFerie.getString("commentaires");
			
			LocalDate date = LocalDate.of(Integer.parseInt(strDate.split("-")[0]),
					Integer.parseInt(strDate.split("-")[1]),
					Integer.parseInt(strDate.split("-")[2]));
			if(serviceAbsence.isJourFerieLibre(date)){
				TypeAbsence type = TypeAbsence.valueOf(strType);
				type.toString();
				Absence newFerie = new Absence();
				newFerie.setDateDebut(date);
				newFerie.setMotif(commentaires);
				newFerie.setType(type);
				newFerie.setStatut((type==TypeAbsence.RTT_EMPLOYEUR)?Statut.INITIALE: Statut.VALIDEE);
				repoAbsence.save(newFerie);
				valid = true;
			}
		}
		
		JSONObject retour = new JSONObject();
		retour.put("success", valid);
		return retour.toString();
	}

}

package dev.rest;

import java.time.format.TextStyle;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.service.ServiceAbsence;

@RestController
@RequestMapping("/ferie")
public class RestFerie {

	@Autowired
	private ServiceAbsence serviceAbsence;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public String getJourFeriesEtRttEmployeur(){
		JSONArray jsonArrayFeries = new JSONArray();
		serviceAbsence.getJoursFeriesEtRttEmployeurs()
			.forEach(currentJour -> {
				JSONObject jsonCurrentFerie = new JSONObject();
				String nomJour = currentJour.getDateDebut().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);
				jsonCurrentFerie.put("id", currentJour.getId())
					.put("date", currentJour.getDateDebut())
					.put("type", currentJour.getType())
					.put("jour", Character.toUpperCase(nomJour.charAt(0)) + nomJour.substring(1))
					.put("commentaires", currentJour.getMotif());
				jsonArrayFeries.put(jsonCurrentFerie);
			});
		return jsonArrayFeries.toString();
	}
	
}

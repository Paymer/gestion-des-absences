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
import dev.service.ServiceCollaborateur;

@RestController
@RequestMapping("/conges-et-rtt")
public class RestCongesEtRtt {

	@Autowired
	private ServiceCollaborateur serviceCollaborateurs;
	
	@RequestMapping(value = "/{matricule}", method = RequestMethod.GET, produces = "application/json")
	public String getCongesEtRtt(@PathVariable String matricule) throws JSONException {
		
		Optional<Collaborateur> oc = serviceCollaborateurs.findCollaborateurParMatricule(matricule);
		
		JSONObject reponse = new JSONObject();
		if(oc.isPresent()){
			Collaborateur c = oc.get();
			
			reponse.put("congesPayes", c.getCongesPayes())
				   .put("rtt", c.getRtt());
		}
		return reponse.toString();
	}
	
}

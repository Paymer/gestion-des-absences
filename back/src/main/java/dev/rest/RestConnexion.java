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
@RequestMapping("/connexion")
public class RestConnexion {
	
	@Autowired
	ServiceCollaborateur serviceCollaborateurs;
	
	@RequestMapping(value = "/{email}/{password}", method = RequestMethod.GET, produces = "application/json")
	public String checkAuth(@PathVariable String email, @PathVariable String password) throws JSONException {
		Optional<Collaborateur> oc = serviceCollaborateurs.checkAuth(email, password);
		JSONObject reponse = new JSONObject();
		if(oc.isPresent()){
			Collaborateur c = oc.get();
			reponse.put("nom", c.getNom())
				.put("prenom", c.getPrenom())
				.put("matricule", c.getMatricule())
				.put("grade", c.getGrade().toString())
				.put("email", c.getEmail())
				.put("departement", c.getDepartement().getLibelle())
				.put("congesPayes", c.getCongesPayes())
				.put("rtt", c.getRtt());
		}
		return reponse.toString();
	}
	
}

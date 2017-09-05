package dev.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.entite.Collaborateur;
import dev.entite.Departement;
import dev.entite.Collaborateur.Grade;

@Service
public class ServiceCollaborateur {

	@Autowired
	private ServiceDepartement serviceDepartements;
	@Autowired
	private ServiceUrls urls;

	private static final Logger LOG = LoggerFactory.getLogger(ServiceCollaborateur.class);

	private List<Collaborateur> listeCollaborateurs = new ArrayList<>();

	@PostConstruct
	public void init() {
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> responseEntity = rt.getForEntity(urls.getCollaborateursUrl(), String.class);
		String result = responseEntity.getBody();

		try {
			JSONArray objects = new JSONArray(result);
			this.createCollaborateurs(objects);
			this.addManagers(objects);
			this.setGrades();
		} catch (JSONException e) {
			LOG.error("Could not get collaborateurs list : ", e.getMessage());
		}
	}

	private void createCollaborateurs(JSONArray objects) throws JSONException {
		for (int index = 0; index < objects.length(); index++) {
			if (objects.get(index) instanceof JSONObject) {
				JSONObject current = (JSONObject) objects.get(index);
				Collaborateur c = new Collaborateur();
				c.setMatricule(current.getString("matricule"));
				c.setNom(current.getString("nom"));
				c.setPrenom(current.getString("prenom"));
				c.setEmail(current.getString("email"));
				c.setPassword(current.getString("password"));
				Optional<Departement> d = serviceDepartements
						.findDepartementParLibelle(current.getString("departement"));
				if (d.isPresent()) {
					c.setDepartement(d.get());
				}
				this.listeCollaborateurs.add(c);
			}
		}
	}

	private void addManagers(JSONArray objects) throws JSONException {
		for (int index = 0; index < objects.length(); index++) {
			if (objects.get(index) instanceof JSONObject) {
				JSONObject current = (JSONObject) objects.get(index);
				Optional<Collaborateur> c = this.findCollaborateurParMatricule(current.getString("matricule"));
				if (c.isPresent()) {
					JSONArray subalternes = current.getJSONArray("subalternes");
					for (int indexSubalterne = 0; indexSubalterne < subalternes.length(); indexSubalterne++) {
						if (subalternes.get(indexSubalterne) instanceof String) {
							Optional<Collaborateur> subalterne = this
									.findCollaborateurParMatricule((String) subalternes.get(indexSubalterne));
							if (subalterne.isPresent()) {
								subalterne.get().setManager(c.get());
								c.get().addSubalterne(subalterne.get());
							}
						}
					}
				}
			}
		}
	}

	private void setGrades() {
		this.listeCollaborateurs.stream().forEach(c -> {
			if(c.getSubalternes().isEmpty()){
				c.setGrade(Grade.Employé);
			}else if(c.getManager().isPresent()){
				c.setGrade(Grade.Manager);
			}else{
				c.setGrade(Grade.Administrateur);
			}
		});
	}

	public List<Collaborateur> findAll() {
		return new ArrayList<>(this.listeCollaborateurs);
	}

	public Optional<Collaborateur> findCollaborateurParMatricule(String matricule) {
		return this.listeCollaborateurs.stream().filter(c -> c.getMatricule().equals(matricule)).findAny();
	}

	public Optional<Collaborateur> checkAuth(String email, String password) {
		return this.listeCollaborateurs.stream()
				.filter(c -> c.getEmail().equalsIgnoreCase(email)
						&& c.getPassword().equals(DigestUtils.shaHex(password)))
				.findAny();
	}

}

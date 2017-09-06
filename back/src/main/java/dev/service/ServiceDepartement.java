package dev.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.entite.Departement;

@Service
public class ServiceDepartement {
	
	@Autowired
	private ServiceUrls urls;
	
	private static final Logger LOG = LoggerFactory.getLogger(ServiceDepartement.class);
	
	private List<Departement> listeDepartements = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> responseEntity = rt.getForEntity(urls.getCollaborateursUrl(), String.class);
		String result = responseEntity.getBody();
		
		try {
			JSONArray objects = new JSONArray(result);
			for(int index = 0; index < objects.length(); index++){
				addDepartementJSON(objects.get(index));
			}
		} catch (JSONException e) {
			LOG.error("Error while creating departements list : ", e.getMessage());
		}
	}

	private void addDepartementJSON(Object collaborateur) throws JSONException {
		if(collaborateur instanceof JSONObject){
			JSONObject collaborateurCourant = (JSONObject) collaborateur;
			Optional<Departement> od = this.findDepartementParLibelle(collaborateurCourant.getString("departement"));
			if(!od.isPresent()){
				Departement d = new Departement();
				d.setLibelle(collaborateurCourant.getString("departement"));
				this.listeDepartements.add(d);
			}
		}
	}
	
	public List<Departement> findAll(){
		return new ArrayList<>(this.listeDepartements);
	}
	
	public Optional<Departement> findDepartementParLibelle(String libelle){
		return this.listeDepartements.stream().filter(d -> d.getLibelle().equals(libelle)).findAny();
	}
	
}

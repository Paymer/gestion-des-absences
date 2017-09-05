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
			//browse departements and add unknowns
			JSONArray objects = new JSONArray(result);
			for(int index = 0; index < objects.length(); index++){
				if(objects.get(index) instanceof JSONObject){
					JSONObject current = (JSONObject) objects.get(index);
					Optional<Departement> od = this.findDepartementParLibelle(current.getString("departement"));
					if(!od.isPresent()){
						Departement d = new Departement();
						d.setLibelle(current.getString("departement"));
						this.listeDepartements.add(d);
					}
				}
			}
		} catch (JSONException e) {
			LOG.error("Could not get departements list : ", e.getMessage());
		}
	}
	
	public List<Departement> findAll(){
		return new ArrayList<>(this.listeDepartements);
	}
	
	public Optional<Departement> findDepartementParLibelle(String libelle){
		return this.listeDepartements.stream().filter(d -> d.getLibelle().equals(libelle)).findAny();
	}
	
}

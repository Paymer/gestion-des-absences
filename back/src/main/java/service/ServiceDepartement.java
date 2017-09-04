package service;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import entite.Departement;

@Configuration
@Service
public class ServiceDepartement {
	
	public List<Departement> findAll(){
		//ServiceCollaborateur.findAll()
		//distinct departements
		//return list
		return null;
	}
	
	public Departement findCollaborateursParDepartement(String matricule){
		//ServiceCollaborateur.findAll()
		//filter by matricule
		//return list
		return null;
	}
	
}

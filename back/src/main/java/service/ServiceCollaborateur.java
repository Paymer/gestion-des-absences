package service;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import entite.Collaborateur;

@Configuration
@Service
public class ServiceCollaborateur {
	
	public List<Collaborateur> findAll(){
		//get data from https://github.com/DiginamicFormation/ressources-atelier/blob/master/users.json
		//parse into collaborateurs
		//return list
		return null;
	}
	
	public Collaborateur findCollaborateurParId(int id){
		//this.findAll()
		//find the right one
		return null;
	}
	
}

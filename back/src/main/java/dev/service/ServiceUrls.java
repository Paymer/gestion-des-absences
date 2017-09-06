package dev.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceUrls {
	
	private String collaborateursUrl = "https://raw.githubusercontent.com/DiginamicFormation/ressources-atelier/master/users.json";
	
	public String getCollaborateursUrl(){
		return this.collaborateursUrl;
	}
	
}

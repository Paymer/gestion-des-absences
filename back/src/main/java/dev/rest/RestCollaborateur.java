package dev.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entite.Collaborateur;
import dev.service.ServiceCollaborateur;

@RestController
@RequestMapping("/collaborateur")
public class RestCollaborateur {

	@Autowired
	private ServiceCollaborateur serviceCollaborateur;
	
	@GetMapping("/{matriculeManager}")
	public List<Collaborateur> findSubalternesParMatriculeManager(@PathVariable String matriculeManager) {
		
		Optional<Collaborateur> collab = serviceCollaborateur.findCollaborateurParMatricule(matriculeManager);
		
		if(collab.isPresent()) {
			return collab.get().getSubalternes();
		}
		
		return null;
	}
	
}

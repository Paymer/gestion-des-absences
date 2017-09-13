package dev.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entite.Departement;
import dev.service.ServiceDepartement;

@RestController
@RequestMapping("/departement")
public class RestDepartement {

	@Autowired
	private ServiceDepartement serviceDepartement;
	
	@GetMapping()
	public List<Departement> findAll() {
		return serviceDepartement.findAll();
	}
	
}
package dev.controller;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.entite.Absence;
import dev.entite.Collaborateur;
import dev.repository.RepositoryAbsence;
import dev.service.ServiceAbsence;
import dev.service.ServiceCollaborateur;


@RestController
@RequestMapping("/histogramme")
public class ControllerHistogramme {
	
	private ServiceAbsence serAbsence;
	private ServiceCollaborateur serCollab;
	private RepositoryAbsence repoAbsence;
	
	
	@Autowired
	public ControllerHistogramme(ServiceAbsence serAbsence, RepositoryAbsence repoAbsence, ServiceCollaborateur serCollab) {
		super();
		this.serAbsence = serAbsence;
		this.repoAbsence = repoAbsence;
		this.serCollab = serCollab;
	}
	
	@GetMapping()
	public List<Absence> listerAbsences() {
		return this.repoAbsence.findAll();
	}
	
	@RequestMapping(value = "/{departement}/{year}/{month}", method = RequestMethod.GET, produces = "application/json")
	public String chartData(@PathVariable String departement, @PathVariable Integer year, @PathVariable Integer month){
		JSONObject data = new JSONObject();
		
		/**get list of the collab of the department
		 * make list of all the absences of each of the collab of the depart
		 * filter that list checking if there is a coincidence between month and year 
		 * get the list and create the new data for the chart
		 */
		
		//list of the people from departement:
		Optional<Collaborateur> listCollab = this.serCollab.findCollaborateurParDepartement(departement);
		
		
		//List<Absence> list = listerAbsences().stream().filter();

	
	
		return data.toString();
	}


}

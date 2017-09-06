package dev.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.repository.RepositoryAbsence;
import dev.service.ServiceAbsence;



@RestController
@RequestMapping("/absence")
public class ControllerAbsence {

	@Autowired
	private RepositoryAbsence repoAbsence;
	
	@Autowired
	private ServiceAbsence serAbsence;

	@GetMapping()
	public List<Absence> listerAbsences() {
		return this.repoAbsence.findAll();
	}
	
	@GetMapping("/{matriculeEmploye}")
	public List<Absence> findAbsenceParMatriculeEmploye(@PathVariable String matriculeEmploye) {
		return this.repoAbsence.findByMatriculeEmploye(matriculeEmploye);
	}


	@RequestMapping(method = RequestMethod.POST, path = "/demande", consumes = "application/json;charset=UTF-8")
	public String ajoutAbsence(@RequestBody Absence newAbsence) {
		newAbsence.setStatut(Statut.INITIALE);

		if (serAbsence.conditions(newAbsence)) {
		repoAbsence.save(newAbsence);
		}

		return "";

	}
	
	@RequestMapping(method = RequestMethod.PUT, path="/modification/{idAbsence}")
	public ModelAndView modifyAbsence (@PathVariable Integer idAbsence, @RequestBody Absence newAbsence){
		//TODO transformer la response du formulaire en JSON
		
		//on recupere la absence a modifier
		Absence absence = new Absence();
		List<Absence> liste = listerAbsences();
		for (Absence a:liste){
			if (idAbsence.equals(a.getId())){
				absence = a;
			}}

		//on complete la newAbsence pour tester les conditions
		newAbsence.setId(idAbsence);
		newAbsence.setMatriculeEmploye(absence.getMatriculeEmploye());
		
		//check si les conditions se sont correctes pour la modifier
			if(serAbsence.conditions(newAbsence)){
				//on remet les infos de le formulaire
				absence.setDateDebut(newAbsence.getDateDebut());
				absence.setDateFin(newAbsence.getDateFin());
				absence.setType(newAbsence.getType());
				absence.setMotif(newAbsence.getMotif());
				absence.setStatut(Statut.INITIALE);
					}
			//addObject?
			repoAbsence.save(absence);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/absence");
			return mv;
	
	
}
	
	
	
		
		
	
}
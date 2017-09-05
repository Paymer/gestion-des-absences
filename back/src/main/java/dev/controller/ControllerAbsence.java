package dev.controller;

import java.time.LocalDate;
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
import dev.entite.Absence.TypeAbsence;
import dev.repository.RepositoryAbsence;


@RestController
@RequestMapping("/absence")
public class ControllerAbsence {

	@Autowired
	private RepositoryAbsence repoAbsence;

	@GetMapping()
	public List<Absence> listerAbsences() {
		return this.repoAbsence.findAll();
	}
	
	@GetMapping("/{idEmploye}")
	public List<Absence> findAbsenceParId(@PathVariable Integer idEmploye) {
		return this.repoAbsence.findByIdEmploye(idEmploye);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/demande")
	public String ajoutAbsence() {
		return "redirect:accuiel";
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

		//check si les conditions se sont correctes pour la modifier
			if(conditions(newAbsence)){
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
	
	
	/**method qui va a reviser toutes les conditions pour fer le post*/
	private boolean conditions (Absence p){
		
		LocalDate t = LocalDate.now();
		boolean exists =  p != null;
		boolean notMission = (p.getType()!= TypeAbsence.MISSION);
		boolean dates = p.getDateDebut().isBefore(p.getDateFin());
		boolean dateInit = p.getDateDebut().isBefore(t) &&  !p.getDateDebut().isEqual(t);
		boolean motif;
		//TODO chevauche avec un autre absence
		if (p.getType() == TypeAbsence.CONGE_SANS_SOLDE && p.getMotif() == null){
			motif = false;
		}else{ motif = true;}
		//TODO ajouter chevauche a le return
		return ( exists && notMission && dates && dateInit && motif  );
		
	}
		
		
		
	
}
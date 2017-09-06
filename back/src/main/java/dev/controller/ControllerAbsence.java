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
	
	@GetMapping("/{matriculeEmploye}")
	public List<Absence> findAbsenceParMatriculeEmploye(@PathVariable String matriculeEmploye) {
		return this.repoAbsence.findByMatriculeEmploye(matriculeEmploye);
	}


	@RequestMapping(method = RequestMethod.POST, path = "/demande", consumes = "application/json;charset=UTF-8")
	public String ajoutAbsence(@RequestBody Absence newAbsence) {
		newAbsence.setStatut(Statut.INITIALE);

		if (conditions(newAbsence)) {
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
	
	
	/**method qui va a reviser toutes les conditions pour faire le post*/
	private boolean conditions (Absence newAbsence){
		
		LocalDate t = LocalDate.now();
		boolean exists =  (newAbsence != null);
		boolean notMission = (newAbsence.getType()!= TypeAbsence.MISSION);
		boolean dates = newAbsence.getDateDebut().isBefore(newAbsence.getDateFin());
		boolean dateInit = newAbsence.getDateDebut().isBefore(t) &&  !newAbsence.getDateDebut().isEqual(t);
		boolean motif;


		if (newAbsence.getType() == TypeAbsence.CONGE_SANS_SOLDE && newAbsence.getMotif() == null){
			motif = false;
		}else{ motif = true;}
		
		boolean chevauche = comprovationChevauche(newAbsence);
		
		return (exists && notMission && dates && dateInit && motif && chevauche );
		
	}
	
	
	//check de la condition chevauche
	private boolean comprovationChevauche (Absence newAbsence){
		
		List<Absence> liste = findAbsenceParMatriculeEmploye(newAbsence.getMatriculeEmploye());
		for (Absence a:liste){
			
			boolean ch1 = a.getDateFin().isBefore(newAbsence.getDateDebut()); //the period was before
			boolean ch2 = a.getDateDebut().isAfter(newAbsence.getDateFin()); //the period was after
			boolean ch3 = a.getId().equals(newAbsence.getId()); // is the same absence
		
			
			if ((!ch1 || !ch2) && !ch3){
				return false;
			}
		
		}
		
		
		
		return true;
	}
		
		
		
	
}
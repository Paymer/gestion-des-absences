package dev.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.controller.ControllerAbsence;
import dev.entite.Absence;
import dev.entite.Absence.TypeAbsence;

@Service
public class ServiceAbsence {

	@Autowired
	private ControllerAbsence ca;
	
	/**method qui va a reviser toutes les conditions pour faire le post*/
	public boolean conditions (Absence newAbsence){
		
		LocalDate t = LocalDate.now();
		
		boolean notMission = (newAbsence.getType()!= TypeAbsence.MISSION);
		boolean dates = newAbsence.getDateDebut().isBefore(newAbsence.getDateFin());
		boolean dateInit = !newAbsence.getDateDebut().isBefore(t) &&  !newAbsence.getDateDebut().isEqual(t);
		boolean motif;


		if (newAbsence.getType() == TypeAbsence.CONGES_SANS_SOLDE && newAbsence.getMotif() == null) {
			motif = false;
		}else{ motif = true;}
		
		boolean chevauche = verificationChevauche(newAbsence);
		
		return (notMission && dates && dateInit && motif && chevauche );
		
	}
	
	
	//check de la condition chevauche
	public boolean verificationChevauche (Absence newAbsence){
		
		List<Absence> liste = ca.findAbsenceParMatriculeEmploye(newAbsence.getMatriculeEmploye());
		for (Absence a:liste){
			
			boolean ch1 = a.getDateFin().isBefore(newAbsence.getDateDebut()); //the period was before
			boolean ch2 = a.getDateDebut().isAfter(newAbsence.getDateFin()); //the period was after
			boolean ch3 = a.getId().equals(newAbsence.getId()); // is the same absence
		
			
			if (!ch1 && !ch2 && !ch3){
				return false;
			}
		
		}
		
		
		
		return true;
	}
		
	
}

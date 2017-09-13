package dev.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import dev.service.ServiceCollaborateur;



@RestController
@RequestMapping("/histogramme")
public class ControllerHistogramme{
	

	private ServiceCollaborateur serCollab;
	private RepositoryAbsence repoAbsence;
	private ControllerAbsence absenceCtrl;

	
	@Autowired
	public ControllerHistogramme(RepositoryAbsence repoAbsence, ServiceCollaborateur serCollab, ControllerAbsence absenceCtrl) {
		super();
		this.absenceCtrl = absenceCtrl;
		this.repoAbsence = repoAbsence;
		this.serCollab = serCollab;
	}
	
	@GetMapping()
	public List<Absence> listerAbsences() {
		return this.repoAbsence.findAll();
	}
	
	@RequestMapping(value = "/{departement}/{year}/{month}", method = RequestMethod.GET, produces = "application/json")
	public String chartData(@PathVariable String departement, @PathVariable Integer year, @PathVariable Integer month){
		//JSONObject data = new JSONObject();
		
		/**get list of the collab of the department
		 * make list of all the absences of each of the collab of the depart
		 * filter that list checking if there is a coincidence between month and year 
		 * get the list and create the new data for the chart
		 */
		
		//list of the people from departement:
		List<Collaborateur> listCollab = this.serCollab.findCollaborateurParDepartement(departement);
		
		
		//creer la parti label (liste de jours)
		String labels = "[";
		LocalDate date = LocalDate.of (year, month, 01);
		while (date.getDayOfMonth() <= date.lengthOfMonth()){
			if (date.getDayOfMonth() != date.lengthOfMonth()){
				labels = labels + date.toString() + ", ";
			}else{
				labels = labels + date.toString();
			}
			date = date.plusDays(1);
		}
			labels = labels + "]";	
		
			
		//creationDataSets
			String datasets = "[";
			for (Collaborateur c : listCollab){
				//TODO Add color
				datasets = datasets + "{ label: '" + c.getPrenom() +"', backgroundColor:" + "COLOOOR"+ "yAxisId:'bar-y-axis', data: " + creerDates(c, month, year) + "},";


			}
			//on fourni le dataset	
			datasets = datasets + "]";
	
			//creation du char data
			String charData  = "{ labels: " + labels + ", datasets : " + datasets +"};";
	
	
		return charData;
	}

	
	public String creerDates(Collaborateur c, int year, int month){
		
		// on recupere une liste des absences de chaque collaborateur
		List<Absence> listAbsences = absenceCtrl.findAbsenceParMatriculeEmploye(c.getMatricule());
		
		//on filtre la liste pour avoir les absences du periode selectione
		 int[] listJours = listAbsences.stream()
				.filter(a -> a.getDateDebut().getYear() == year ||  a.getDateFin().getYear() == year) //year
				.filter(a -> a.getDateDebut().getMonthValue() == month ||  a.getDateFin().getMonthValue() == month)//month
				.map(a -> transform(a, year, month))
				.reduce((tableauPrecedent, tableauActuel) -> {
					Arrays.setAll(tableauPrecedent, indexTableauPrecedent -> {
						int valeurTableauActuel = tableauActuel[indexTableauPrecedent];
						int valeurTableauPrecedent = tableauPrecedent[indexTableauPrecedent];
						return valeurTableauActuel + valeurTableauPrecedent;
					});
					return tableauActuel;
				}).get();
		
		return "[" + listJours.toString() + "]";
	}
	
	// transform an absence into an array
	public int[] transform (Absence a, int year, int month){
		LocalDate d = LocalDate.of(year, month, 1);
		int[] jours = new int[d.lengthOfMonth()];
		if (a.getDateDebut().isAfter(d))
			d = a.getDateDebut();

		while (d.isBefore(a.getDateFin()) && d.getMonthValue() == month){
			//TODO Add the jours feries option
			if (d.getDayOfWeek().equals(DayOfWeek.SATURDAY) || d.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
				jours[d.getDayOfMonth()-1] = 0;
			}else{
				jours[d.getDayOfMonth()-1] = 1;
			}
			d = d.plusDays(1);}
		return jours; 
	}
	

}

package dev.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
	
/**
 * this method will create an string for the barchart
 */
	@RequestMapping(value = "/{departement}/{year}/{month}", method = RequestMethod.GET, produces = "application/json")
	public String chartData(@PathVariable String departement, @PathVariable Integer year, @PathVariable Integer month){
		//JSONObject data = new JSONObject();
		
		//list of the people from departement:
		List<Collaborateur> listCollab = this.serCollab.findCollaborateurParDepartement(departement);

		//creer la parti label (liste de jours)
		String labels = creerLabel(year, month);
	
		//creationDataSets
			String datasets = "[";
			int i = 0;
			for (Collaborateur c : listCollab){
				datasets = datasets + "{ label: '" + c.getNom() +"', backgroundColor:" + getColor(i)+ "yAxisId:'bar-y-axis', data: " + creerDates(c, month, year) + "},";
				i++;
			}
			datasets = datasets + "]";
			
		return "{ labels: " + labels + ", datasets : " + datasets +"};";
	}
	
	
	public String getColor (int i){
		String[] color = {"aliceblue", "antiquewhite", "aqua", "aquamarine", "azure", "beige", "bisque", "black", "blanchedalmond", "blue", "blueviolet", 
				"brown", "burlywood", "cadetblue", "chartreuse", "chocolate", "coral", "cornflowerblue", "cornsilk", "crimson", "cyan", "darkblue", "darkcyan", 
				"darkgoldenred", "darkgray", "darkgreen", "darkkhaki", "darkmagenta", "darkolivegreen", "darkorange", "darkorchid", "darkred", "darksalmon", 
				"darkseagreen", "darkslateblue", "darkslategray", "darkturquoise", "darkviolet", "deeppink", "deepskyblue", "dimgray", "dodgerblue", 
				"firebrick", "floralwhite", "forestgreen", "fuchsia", "gainsboro", "ghostwhite", "gold", "goldenrod", "gray", "green", "greenyellow", "honeydew",
				"hotpink", "indianred", "indigo", "ivory", "khaki", "lavender", "lavenderblush", "lawngreen", "lemonchiffon", "lightblue", "lightcoral",
				"lightcyan", "lightgoldenrodyellow", "lightgreen", "lightgrey", "lightpink", "lightsalmon", "lightseagreen", "lightskyblue", "lightslategray",
				"lightsteelblue", "lightyellow", "lime", "limegreen", "linen", "magenta", "maroon", "mediumaquamarine", "mediumblue", "mediumorchid", "mediumpurple",
				"mediumseagreen", "mediumslateblue", "mediumspringgreen", "mediumturquoise", "mediumvioletred", "midnightblue", "mintcream", "mistyrose",
				"moccasin", "navajowhite", "navy", "oldlace", "olive", "olivedrab", "orange", "orangered", "orchid", "palegoldenrod", "palegreen", "paleturquoise",
				"palevioletred", "papayawhip", "peachpuff", "peru", "pink", "plum", "powderblue", "purple", "red", "rosybrown", "royalblue", "saddlebrown", "salmon", "sandybrown",
				"seagreen", "seashell", "sienna", "silver", "skyblue", "slateblue", "slategray", "snow", "springgreen", "steelblue", "tan", "teal", "thistle", "tomato",
				"turquoise", "violet", "wheat", "whitesmoke", "yellow", "yellowgreen" };
		
		return "'"+color[i]+"'";
	}
	
	public String creerLabel (int year, int month){
		
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
		
		return labels;
	}

	/**
	 * it will create a list 
	 * 0->not absence/ferie/weekend
	 * 1->absence
	 * for the collaborateur, for all the month
	 */
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
	
	
	/**
	 * it transforms an absence into an array
	 * the array has the month size
	 * it places 1 where there is an absence
	 */
	// transform an absence into an array
	public int[] transform (Absence a, int year, int month){
		LocalDate d = LocalDate.of(year, month, 1);
		int[] jours = new int[d.lengthOfMonth()];
		if (a.getDateDebut().isAfter(d))
			d = a.getDateDebut();

		while (d.isBefore(a.getDateFin()) && d.getMonthValue() == month){

			if (d.getDayOfWeek().equals(DayOfWeek.SATURDAY) || d.getDayOfWeek().equals(DayOfWeek.SUNDAY) || isJour(d)){
				jours[d.getDayOfMonth()-1] = 0;
			}else{
				jours[d.getDayOfMonth()-1] = 1;
			}
			d = d.plusDays(1);}
		return jours; 
	}
	
	//it will check if the day is ferie or rtt employee
	public boolean isJour(LocalDate d){
		
		boolean is = false;
	/**	ArrayList<Absence> feries = ;//TODO recuperer la liste de jours feries and rtts
		for (Absence a:feries){
			if (a.getDateDebut().isEqual(d)){
				is = true;
			}
		}*/
		
		return is;
	}

}

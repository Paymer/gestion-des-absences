package dev.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.controller.ControllerAbsence;
import dev.entite.Absence;
import dev.entite.Collaborateur;
import dev.entite.histogramme.BarChartData;
import dev.entite.histogramme.Set;


@Service
public class ServiceHistogramme {

	private ServiceCollaborateur serCollab;
	private ControllerAbsence absenceCtrl;
	private ServiceAbsence serAbs;

	
	@Autowired
	public ServiceHistogramme(ServiceCollaborateur serCollab, ControllerAbsence absenceCtrl, ServiceAbsence serAbs) {
		super();
		this.absenceCtrl = absenceCtrl;
		this.serCollab = serCollab;
		this.serAbs = serAbs;
	}
	
	/**
	 * this method will create an string for the barchart
	 */
	public BarChartData chartData(String departement, int year, int month ){
		
		BarChartData barChartData = new BarChartData();
		//list of the people from departement:
		List<Collaborateur> listCollab = this.serCollab.findCollaborateurParDepartement(departement);
		//creer la parti label (liste de jours)
		barChartData.setLabels(creerLabel(year, month));

		//creationDataSets
			List<Set> datasets = new ArrayList<>();
			int i = 0;
			for (Collaborateur c : listCollab){
				Set set=new Set();
				set.setLabel(c.getNom());
				set.setBackgroundColor(getColor(i));
				set.setData(creerDates(c, year, month));
				datasets.add(set);
				i++;
			}
			barChartData.setDatasets(datasets);
		return barChartData;
	}
	
	public String getColor (int i){
		String[] color = {"darkviolet", "darkturquoise","olive", "plum", "antiquewhite", "aqua", "aquamarine", "azure", "beige", "bisque", "black", "blanchedalmond", "blue", "blueviolet", 
				"brown", "burlywood", "cadetblue", "chartreuse", "chocolate", "coral", "cornflowerblue", "cornsilk", "crimson", "cyan", "darkblue", "darkcyan", 
				"darkgoldenred", "darkgray", "darkgreen", "darkkhaki", "darkmagenta", "darkolivegreen", "darkorange", "darkorchid", "darkred", "darksalmon", 
				"darkseagreen", "darkslateblue", "darkslategray", "darkturquoise",  "deeppink", "deepskyblue", "dimgray", "dodgerblue", 
				"firebrick", "floralwhite", "forestgreen", "fuchsia", "gainsboro", "ghostwhite", "gold", "goldenrod", "gray",  "honeydew",
				"hotpink", "indianred", "indigo", "ivory", "khaki", "lavender", "lavenderblush", "lawngreen", "lemonchiffon", "lightblue", "lightcoral",
				"lightcyan", "lightgoldenrodyellow", "lightgreen", "lightgrey", "lightpink", "lightsalmon", "lightseagreen", "lightskyblue", "lightslategray",
				"lightsteelblue", "lightyellow", "lime", "limegreen", "linen", "magenta", "maroon", "mediumaquamarine", "mediumblue", "mediumorchid", "mediumpurple",
				"mediumseagreen", "mediumslateblue", "mediumspringgreen", "mediumturquoise","yellow","greenyellow", "pink","red","green",  "mediumvioletred", "midnightblue", "mintcream", "mistyrose",
				"moccasin", "navajowhite", "navy", "oldlace",  "olivedrab", "orange", "orangered", "orchid", "palegoldenrod", "palegreen", "paleturquoise",
				"palevioletred", "papayawhip", "peachpuff", "peru",   "powderblue", "purple",  "rosybrown", "royalblue", "saddlebrown", "salmon", "sandybrown",
				"seagreen", "seashell", "sienna", "silver", "skyblue", "slateblue", "slategray", "snow", "springgreen", "steelblue", "tan", "teal", "thistle", "tomato",
				"turquoise", "violet", "wheat", "whitesmoke",  "yellowgreen", "aliceblue" };
		
		return  color[i];
	}
	
	/**
	*create a list of labels
	 */
	
	public String[] creerLabel (int year, int month){
		LocalDate date = LocalDate.of (year, month, 01);
		String[] labels = new String[date.lengthOfMonth()];
	
		
		while (date.getDayOfMonth() <= date.lengthOfMonth() && month == date.getMonthValue()){
			labels[date.getDayOfMonth()-1] = date.toString();
			date = date.plusDays(1);
			
		}
		return labels;
	}

	/**
	 * it will create a list 
	 * 0->not absence/ferie/weekend
	 * 1->absence
	 * for the collaborateur, for all the month
	 */
	public int[] creerDates(Collaborateur c, int year, int month){
		
		// on recupere une liste des absences de chaque collaborateur
		List<Absence> listAbsences = absenceCtrl.findAbsenceParMatriculeEmploye(c.getMatricule());
		//List<Absence> listAbsences = absenceCtrl.listerAbsences().stream().filter(a -> a.getMatriculeEmploye().equals(c.getMatricule())).collect(Collectors.toList());
		
		//on filtre la liste pour avoir les absences du periode selectione
		 Optional<int[]> result = listAbsences.stream()
				.filter(a -> a.getDateDebut().getYear() == year ||  a.getDateFin().getYear() == year) //year
				.filter(a -> a.getDateDebut().getMonthValue() == month ||  a.getDateFin().getMonthValue() == month)//month
				.map(a -> transform(a, year, month))
				.reduce((tableauPrecedent, tableauActuel) -> {
					Arrays.setAll(tableauPrecedent, indexTableauPrecedent -> {
						int valeurTableauActuel = tableauActuel[indexTableauPrecedent];
						int valeurTableauPrecedent = tableauPrecedent[indexTableauPrecedent];
						return valeurTableauActuel + valeurTableauPrecedent;
					});
					return tableauPrecedent;
				});
		 LocalDate date = LocalDate.of(year, month, 01);
		 return result.isPresent() ? result.get() : new int[date.lengthOfMonth()];
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

		while (d.isBefore(a.getDateFin().plusDays(1)) && d.getMonthValue() == month){

			if (d.getDayOfWeek().equals(DayOfWeek.SATURDAY) || d.getDayOfWeek().equals(DayOfWeek.SUNDAY) || isJour(d)){
				jours[d.getDayOfMonth()-1] = 0;
			}else{
				jours[d.getDayOfMonth()-1] = 1;
			}
			d = d.plusDays(1);
		}
		
		
		return jours; 
	}
	
	//it will check if the day is ferie or rtt employee
	public boolean isJour(LocalDate d){
		
		boolean is = false;
	List<Absence> feries = serAbs.getJoursFeriesEtRttEmployeurs();
	
		for (Absence a:feries){
			if (a.getDateDebut().isEqual(d)){
				is = true;
			}
		}
		
		return is;
	}

}

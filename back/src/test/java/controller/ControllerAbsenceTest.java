package controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.App;
import dev.controller.ControllerAbsence;
import dev.entite.Absence;
import dev.entite.Absence.TypeAbsence;
import dev.repository.RepositoryAbsence;



@RunWith(SpringRunner.class)
@SpringBootTest(classes={ App.class})
public class ControllerAbsenceTest {

	Absence absence = new Absence();
	
	@Autowired
	private ControllerAbsence ca;
	
	@Test
	public void verificationChevaucheTest(){
		
		/**M1 have 2 absences programmed
		 * 2017-05-01 -> 2017-05-04
		 * 2017-11-06 -> 2017-11-24 */
		
		// chevauche 
		String matricule = "M1";
		LocalDate init = LocalDate.of(2017, 05, 02);
		LocalDate fin = LocalDate.of(2017, 05, 03);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
		absence.setMatriculeEmploye(matricule);
		
		boolean result = ca.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);
		
		// chevauche2
				
		init = LocalDate.of(2017, 04, 30);
		fin = LocalDate.of(2017, 05, 24);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
		
		result = ca.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);
			
		// chevauche3	
		init = LocalDate.of(2017, 05, 01);
		fin = LocalDate.of(2017, 05, 04);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
				
		result = ca.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);
		
		//partialChevauche1
		init = LocalDate.of(2017, 05, 02);
		fin = LocalDate.of(2017, 05, 03);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
			
		result = ca.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);
		
		//partialChevauche2
		init = LocalDate.of(2017, 05, 03);
		fin = LocalDate.of(2017, 05, 010);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
					
		result = ca.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);
		
		//pas de chevauche1 after
		init = LocalDate.of(2017, 12, 01);
		fin = LocalDate.of(2017, 12, 04);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
		
		result = ca.verificationChevauche(absence);
		assertThat(result).isEqualTo(true);
			
		//pas de chevauche2 before
		init = LocalDate.of(2017, 10, 01);
		fin = LocalDate.of(2017, 10, 04);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
			
		result = ca.verificationChevauche(absence);
		assertThat(result).isEqualTo(true);
		
		//is the same absence
		//partialChevauche1
		init = LocalDate.of(2017, 05, 02);
		fin = LocalDate.of(2017, 05, 03);
		absence.setId(1);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
					
		result = ca.verificationChevauche(absence);
		assertThat(result).isEqualTo(true);
		
		
	}
	
	@Test
	public void conditionsTest(){
		
		//CORRECT
		//pas de chevauche1 after
		LocalDate init = LocalDate.of(2017, 12, 01);
		LocalDate fin = LocalDate.of(2017, 12, 04);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
		String matricule = "M1";
		absence.setMatriculeEmploye(matricule);
		
		absence.setType(TypeAbsence.CONGES_PAYES);
		//assertThat(absence.getType()!= TypeAbsence.MISSION).isEqualTo(true);
		//assertThat(!absence.getDateDebut().isBefore(LocalDate.now())).isEqualTo(true);
		//assertThat(!absence.getDateDebut().isEqual(LocalDate.now())).isEqualTo(true);
		
		
		
		
		//le motif c'est pas necessaire pour conge paye

		boolean result = ca.conditions(absence);
		assertThat(result).isEqualTo(true);
	
		
		//test mission il donne faux
		absence.setType(TypeAbsence.MISSION);
		result = ca.conditions(absence);
		assertThat(result).isEqualTo(false);
		
		absence.setType(TypeAbsence.CONGES_SANS_SOLDE);
		
		
		//test motifNull = false
		result = ca.conditions(absence);
		assertThat(result).isEqualTo(false);
		
		
		//test motifNull = false
		absence.setMotif("xxx");
		result = ca.conditions(absence);
		assertThat(result).isEqualTo(true);
		
		// incorrect dates - fin before init
		absence.setDateDebut(fin);
		absence.setDateFin(init);
		result = ca.conditions(absence);
		assertThat(result).isEqualTo(false);
		
		// incorrect dates -start now
		absence.setDateDebut(LocalDate.now());
		result = ca.conditions(absence);
		assertThat(result).isEqualTo(false);
		
		// incorrect dates - start in the past
		absence.setDateDebut(LocalDate.of(2016, 01, 01));
		result = ca.conditions(absence);
		assertThat(result).isEqualTo(false);
		
	}
	
}

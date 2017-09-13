package controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.App;
import dev.controller.ControllerAbsence;
import dev.controller.ControllerHistogramme;
import dev.entite.Absence;
import dev.entite.Collaborateur;
import dev.repository.RepositoryAbsence;
import dev.service.ServiceCollaborateur;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={App.class})
public class ControllerHistogrammeTest {

	private ServiceCollaborateur mockServiceCollaborateur;
	private RepositoryAbsence mockRepositoryAbsence;
	private ControllerAbsence mockAbsenceCtrl;
	private ControllerHistogramme ctrlHistogramme;
	
	@Before
	public void init() {
		//injection of the answer of the conditions method in service Absence
		mockAbsenceCtrl = mock(ControllerAbsence.class);
		mockServiceCollaborateur = mock(ServiceCollaborateur.class);
		mockRepositoryAbsence = mock(RepositoryAbsence.class);

		ctrlHistogramme  = new ControllerHistogramme(mockRepositoryAbsence, mockServiceCollaborateur, mockAbsenceCtrl); 
		
		
		creerdb();
	}
	
	
	public void creerdb(){
		//listes pour injecter
		List<Collaborateur> dbColab = new ArrayList <Collaborateur>(); //liste de collab per departement
		List<Absence> dbAbsc1 = new ArrayList<Absence>(); //liste des absences du collab choisi
		List<Absence> dbAbsc2 = new ArrayList<Absence>(); //liste des absences du collab choisi
		List<Absence> dbjours = new ArrayList<Absence>(); //liste des absences (jours feries et rtts)
		
		//liste collaborateurs
		Collaborateur c1 = new Collaborateur();
		c1.setMatricule("c1");
		c1.setNom("c1");
		dbColab.add(c1);
		
		Collaborateur c2 = new Collaborateur();
		c2.setMatricule("c2");
		c2.setNom("c2");
		dbColab.add(c2);
	
		
		//absences collaborateur c1
		Absence a1 = new Absence();
		a1.setDateDebut(LocalDate.of(2016, 12, 28));
		a1.setDateFin(LocalDate.of(2017, 01, 04));
		dbAbsc1.add(a1);
		
		Absence a2 = new Absence();
		a2.setDateDebut(LocalDate.of(2017, 01, 11));
		a2.setDateFin(LocalDate.of(2017, 01, 17));
		dbAbsc1.add(a2);
		
		Absence a3 = new Absence();
		a3.setDateDebut(LocalDate.of(2017, 01, 20));
		a3.setDateFin(LocalDate.of(2017, 01, 26));
		dbAbsc1.add(a3);
		
		Absence a4 = new Absence();
		a4.setDateDebut(LocalDate.of(2017, 01, 27));
		a4.setDateFin(LocalDate.of(2017, 02, 01));
		dbAbsc1.add(a4);
		
		
		
		//absences collaborateur c2
				Absence a5 = new Absence();
				a5.setDateDebut(LocalDate.of(2016, 01, 03));
				a5.setDateFin(LocalDate.of(2017, 01, 10));
				dbAbsc2.add(a5);
				
				Absence a6 = new Absence();
				a6.setDateDebut(LocalDate.of(2017, 01, 16));
				a6.setDateFin(LocalDate.of(2017, 01, 17));
				dbAbsc2.add(a6);
			
		
		//jours feries
		Absence jf1 = new Absence();
		jf1.setDateDebut(LocalDate.of(2017, 1, 02));
		dbjours.add(jf1);
		
		Absence jf2 = new Absence();
		jf2.setDateDebut(LocalDate.of(2017, 1, 03));
		dbjours.add(jf2);
	}
	
	@Test
	public void transformTest(){
	
			
	}
	
	@Test
	public void creerDatesTest(){

			
	}
	
	@Test
	public void chartDataTest(){

			
	}

	
	
}

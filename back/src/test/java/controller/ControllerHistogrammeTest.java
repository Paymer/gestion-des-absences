package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
		LocalDate d =LocalDate.of(2017, 01, 01);
		when(ctrlHistogramme.isJour(d)).thenReturn(false);
		
		LocalDate e =LocalDate.of(2017, 01, 02);
		when(ctrlHistogramme.isJour(e)).thenReturn(false);
		
		LocalDate f =LocalDate.of(2017, 01, 03);
		when(ctrlHistogramme.isJour(f)).thenReturn(true);
		
		LocalDate g =LocalDate.of(2017, 01, 04);
		when(ctrlHistogramme.isJour(g)).thenReturn(false);
		
		
		Absence a1 = new Absence();
		a1.setDateDebut(LocalDate.of(2016, 12, 28));
		a1.setDateFin(LocalDate.of(2017, 01, 04));
	
		int[] jours = ctrlHistogramme.transform(a1, 2017, 01);
		int[] expected = new int[31];
		expected[0] = 0;//weekend
		expected[1] = 1;
		expected[2] = 0;//testing condition jours feries
		expected[3] = 1;
		
		
		assertArrayEquals(jours, expected);
	
		
	
			
	}
	
	/**
	 * In this test the transform is not injected
	 */
	@Test
	public void creerDatesTest(){
		
		//collaborateur
		Collaborateur c1 = new Collaborateur();
		c1.setMatricule("c1");
		c1.setNom("c1");
		
		//liste des absences du collab choisi
		List<Absence> dbAbsc1 = new ArrayList<Absence>(); 
		
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
		
		//there is no jours feries or rtt employee
		when(ctrlHistogramme.isJour(anyObject())).thenReturn(false);
		
		when(mockAbsenceCtrl.findAbsenceParMatriculeEmploye(c1.getMatricule())).thenReturn(dbAbsc1);
		String creer = ctrlHistogramme.creerDates(c1, 2017, 01);
		
		int[] expected = new int[31];
			//absence a1
			expected[1] = 1;
			expected[2] = 1;
			expected[3] = 1;
		
			//absence a2
			expected[10] = 1;
			expected[11] = 1;
			expected[12] = 1;
			expected[15] = 1;
			expected[16] = 1;
				
			//absence a3
			expected[19] = 1;
			expected[22] = 1;
			expected[23] = 1;
			expected[24] = 1;
			expected[25] = 1;
				
			//absence a4	
			expected[26] = 1;
			expected[29] = 1;
			expected[30] = 1;
			
		
		assertThat(creer).containsOnlyOnce("["+expected.toString()+"]");
	}
	
	@Test
	public void chartDataTest(){
		List<Collaborateur> dbColab = new ArrayList <Collaborateur>(); //liste de collab per departement
		
		//liste collaborateurs
		Collaborateur c1 = new Collaborateur();
		c1.setMatricule("c1");
		c1.setNom("c1");
		dbColab.add(c1);
		
		Collaborateur c2 = new Collaborateur();
		c2.setMatricule("c2");
		c2.setNom("c2");
		dbColab.add(c2);
		
		when(mockServiceCollaborateur.findCollaborateurParDepartement("departement")).thenReturn(dbColab);
		when(ctrlHistogramme.creerLabel(2017, 01)).thenReturn("LABEL");
		when(ctrlHistogramme.getColor(0)).thenReturn("'green'");
		when(ctrlHistogramme.getColor(1)).thenReturn("'red'");
		when(ctrlHistogramme.creerDates(c1, 01, 2017)).thenReturn("DATES C1");
		when(ctrlHistogramme.creerDates(c2, 01, 2017)).thenReturn("DATES C2");

		
		String data = ctrlHistogramme.chartData("departement", 2017, 01);
		String expected = "{ labels: " + "'LABEL'" + ", datasets : "+"["+
				//dataset c1
				"{ label: '" + "c1" +"', backgroundColor:" + "'green'" + "yAxisId:'bar-y-axis', data: " + "DATES C1" + "},"+
				//dataset c2
				"{ label: '" + "c2" +"', backgroundColor:" + "'red'" + "yAxisId:'bar-y-axis', data: " + "DATES C2" + "},"+
				
				"]"+	"};";
				

		assertThat(data).isEqualTo(expected);
			
	}
	
	@Test
	public void isJourTest(){
		//mockito inject a list of jours feries
		
		LocalDate d = LocalDate.of(2017, 01, 03);
		assertThat(ctrlHistogramme.isJour(d));
		
		d = LocalDate.of(2017, 01, 04);
		assertThat(!ctrlHistogramme.isJour(d));
	}
	
	
	
	
}

package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
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
import dev.entite.Absence;
import dev.entite.Collaborateur;
import dev.entite.histogramme.BarChartData;
import dev.entite.histogramme.Set;
import dev.service.ServiceAbsence;
import dev.service.ServiceCollaborateur;
import dev.service.ServiceHistogramme;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={App.class})
public class ServiceHistogrammeTest {

	private ServiceCollaborateur mockServiceCollaborateur;
	private ControllerAbsence mockAbsenceCtrl;
	private ServiceHistogramme serHisto;
	private ServiceAbsence mockSerAbs;
	
	

	@Before
	public void init() {
		//injection of the answer of the conditions method in service Absence
		mockAbsenceCtrl = mock(ControllerAbsence.class);
		mockServiceCollaborateur = mock(ServiceCollaborateur.class);
		mockSerAbs = mock(ServiceAbsence.class);
		serHisto  = spy(new ServiceHistogramme( mockServiceCollaborateur, mockAbsenceCtrl, mockSerAbs)) ; 

	}
	
	
	@Test
	public void transformTest(){
		LocalDate d =LocalDate.of(2017, 01, 01);
		doReturn(false).when(serHisto).isJour(d);
		
		LocalDate e =LocalDate.of(2017, 01, 02);
		doReturn(false).when(serHisto).isJour(e);
		
		LocalDate f =LocalDate.of(2017, 01, 03);
		doReturn(true).when(serHisto).isJour(f);
		
		LocalDate g =LocalDate.of(2017, 01, 04);
		doReturn(false).when(serHisto).isJour(g);
		
		
		Absence a1 = new Absence();
		a1.setDateDebut(LocalDate.of(2016, 12, 28));
		a1.setDateFin(LocalDate.of(2017, 01, 04));
	
		
		int[] expected = new int[31];
		expected[0] = 0;
		expected[1] = 1;
		expected[2] = 0;
		expected[3] = 1;
		
		int[] creer = serHisto.transform(a1, 2017, 01);
		assertArrayEquals(creer, expected);
	
		
	
			
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
		 doReturn(false).when(serHisto).isJour(anyObject());
		
		
		when(mockAbsenceCtrl.findAbsenceParMatriculeEmploye(c1.getMatricule())).thenReturn(dbAbsc1);
		
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
			
		int[] dates=serHisto.creerDates(c1, 2017, 01);
		assertThat(dates).isEqualTo(expected);
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
		doReturn(new String[31]).when(serHisto).creerLabel(2017, 01);
		
		doReturn("'green'").when(serHisto).getColor(0);
		doReturn("'red'").when(serHisto).getColor(1);
		doReturn(new int[31]).when(serHisto).creerDates(c1, 2017, 01);
		doReturn(new int[31]).when(serHisto).creerDates(c2, 2017, 01);
		
		BarChartData data = serHisto.chartData("departement", 2017, 01);
		BarChartData expected = new BarChartData();
		
		List<Set> datasets = new ArrayList<Set>();
		
		Set set1 = new Set();
		set1.setLabel("c1");
		set1.setBackgroundColor("'green'");
		set1.setData(new int[31]);
		datasets.add(set1);
		
		Set set2 = new Set();
		set2.setLabel("c2");
		set2.setBackgroundColor("'red'");
		set2.setData(new int[31]);
		datasets.add(set2);
		
		expected.setDatasets(datasets);
		expected.setLabels(new String[31]);
		
		assertThat(expected.getLabels()).isEqualTo(data.getLabels());
		
		assertThat(expected.getDatasets().get(0).getBackgroundColor()).isEqualTo(data.getDatasets().get(0).getBackgroundColor());
		assertThat(expected.getDatasets().get(0).getLabel()).isEqualTo(data.getDatasets().get(0).getLabel());
		assertThat(expected.getDatasets().get(0).getData()).isEqualTo(data.getDatasets().get(0).getData());
		assertThat(expected.getDatasets().get(0).getyAxisID()).isEqualTo(data.getDatasets().get(0).getyAxisID());
		
		assertThat(expected.getDatasets().get(1).getBackgroundColor()).isEqualTo(data.getDatasets().get(1).getBackgroundColor());
		assertThat(expected.getDatasets().get(1).getLabel()).isEqualTo(data.getDatasets().get(1).getLabel());
		assertThat(expected.getDatasets().get(1).getData()).isEqualTo(data.getDatasets().get(1).getData());
		assertThat(expected.getDatasets().get(1).getyAxisID()).isEqualTo(data.getDatasets().get(1).getyAxisID());
	
		//assertThat(data).isEqualTo(expected);
		
			
	}
	
	@Test
	public void isJourTest(){
		
		
		List<Absence> dbjours = new ArrayList<Absence>(); //liste des absences (jours feries et rtts)
		//jours feries
		Absence jf1 = new Absence();
		jf1.setDateDebut(LocalDate.of(2017, 1, 02));
		dbjours.add(jf1);
		
		Absence jf2 = new Absence();
		jf2.setDateDebut(LocalDate.of(2017, 1, 03));
		dbjours.add(jf2);
		
		//mockito inject a list of jours feries
		when(mockSerAbs.getJoursFeriesEtRttEmployeurs()).thenReturn(dbjours);
		
		LocalDate d = LocalDate.of(2017, 01, 03);
		assertThat(serHisto.isJour(d));
		
		d = LocalDate.of(2017, 01, 04);
		assertThat(!serHisto.isJour(d));
	}
	
	
	
	
}

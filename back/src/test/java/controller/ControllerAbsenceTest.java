package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.App;
import dev.controller.ControllerAbsence;
import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.repository.RepositoryAbsence;
import dev.service.ServiceAbsence;
import dev.service.ServiceCollaborateur;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={App.class})
public class ControllerAbsenceTest {
	
	private ControllerAbsence ctrlAbsence;
	private ServiceAbsence mockServiceAbsence;
	private ServiceCollaborateur mockServiceCollaborateur;
	private RepositoryAbsence mockRepositoryAbsence;
	
	
	@Before
	public void init() {
		//injection of the answer of the conditions method in service Absence
		mockServiceAbsence = mock(ServiceAbsence.class);
		mockServiceCollaborateur = mock(ServiceCollaborateur.class);
		mockRepositoryAbsence = mock(RepositoryAbsence.class);
		
		
		ctrlAbsence  = new ControllerAbsence(mockServiceAbsence, mockRepositoryAbsence, mockServiceCollaborateur);
		 
	}
	
	
	
	/**
	 * 
	 * Test liste des absence
	 * 
	 */

	@Test
	public void listerAbsenceTest(){

		ctrlAbsence.listerAbsences();
		verify(mockRepositoryAbsence).findAll();
			
	}
	
	/**
	 * 
	 * Test find absence par matricule
	 * 
	 */

	@Test
	public void findAbsenceParMatriculeEmployeTest(){

		ctrlAbsence.findAbsenceParMatriculeEmploye("M1");
		verify(mockRepositoryAbsence).findByMatriculeEmploye("M1");
			
	}
	
	
	
	/**
	 * Test du method POST
	 */
	@Test
	public void ajoutAbsenceSuccesTest() {
		Absence absence = new Absence();
		when(mockServiceAbsence.conditions(absence)).thenReturn(true);
		// call method POST
		assertThat(ctrlAbsence.ajoutAbsence(absence)).isEqualTo("{\"succes\" : true}");
		assertThat(absence.getStatut()).isEqualTo(Statut.INITIALE);
		verify(mockRepositoryAbsence).save(absence);

	}

	@Test
	public void ajoutAbsenceFailTest() {
		Absence absence = new Absence();
		when(mockServiceAbsence.conditions(absence)).thenReturn(false);
		// call method POST
		assertThat(ctrlAbsence.ajoutAbsence(absence)).isEqualTo("{\"succes\" : false}");
		assertThat(absence.getStatut()).isNull();
		

	}
	
	
	/**
	 * Test du method PUT
	 */
	@Test
	public void modifyAbsenceSuccesTest() {

		Absence absence = new Absence();
		when(mockServiceAbsence.conditions(absence)).thenReturn(true);
		//call method PUT
		assertThat(ctrlAbsence.modifyAbsence(1, absence)).isEqualTo("{\"succes\" : true}");
		assertThat(absence.getStatut()).isEqualTo(Statut.INITIALE);
		
		verify(mockRepositoryAbsence).save(absence);
		
		
	}

	@Test
	public void modifyAbsenceFailTest() {

		Absence absence = new Absence();
		when(mockServiceAbsence.conditions(absence)).thenReturn(false);
		// call method PUT
		assertThat(ctrlAbsence.modifyAbsence(1, absence)).isEqualTo("{\"succes\" : false}");
		assertThat(absence.getStatut()).isNull();

	}
}

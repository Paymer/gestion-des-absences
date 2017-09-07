package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.App;
import dev.controller.ControllerAbsence;
import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.entite.Absence.TypeAbsence;
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
	
	
	
	@Test
	public void listerAbsenceTest(){

		ctrlAbsence.listerAbsences();
		verify(mockRepositoryAbsence).findAll();
			
	}
	
	@Test
	public void findAbsenceParMatriculeEmployeTest(){

		ctrlAbsence.findAbsenceParMatriculeEmploye("M1");
		verify(mockRepositoryAbsence).findByMatriculeEmploye("M1");
			
	}
	
	
	
	/**
	 * Test du method POST
	 */
	@Test
	public void ajoutAbsenceTest(){
		Absence absence = new Absence();
		
		absence.setId(1);
		absence.setStatut(Statut.VALIDEE);
		absence.setDateDebut(LocalDate.of(2017, 5, 6));
		absence.setDateFin(LocalDate.of(2017, 6, 9));
		absence.setMatriculeEmploye("M011");
		absence.setMotif(null);
		absence.setType(TypeAbsence.RTT);
		when(mockServiceAbsence.conditions(absence)).thenReturn(true);
		//call method PUT
		ctrlAbsence.ajoutAbsence(absence);
		assertThat(absence.getStatut()).isEqualTo(Statut.INITIALE);
		
		verify(mockRepositoryAbsence).save(absence);
		
		
	}
	
	
	/**
	 * Test du method PUT
	 */
	@Test
	public void modifyAbsenceTest(){
		Absence absence = new Absence();
		
		absence.setId(1);
		absence.setStatut(Statut.VALIDEE);
		absence.setDateDebut(LocalDate.of(2017, 5, 6));
		absence.setDateFin(LocalDate.of(2017, 6, 9));
		absence.setMatriculeEmploye("M011");
		absence.setMotif(null);
		absence.setType(TypeAbsence.RTT);
		when(mockServiceAbsence.conditions(absence)).thenReturn(true);
		//call method PUT
		ctrlAbsence.modifyAbsence(1, absence);
		assertThat(absence.getStatut()).isEqualTo(Statut.INITIALE);
		
		verify(mockRepositoryAbsence).save(absence);
		
		
	}
}

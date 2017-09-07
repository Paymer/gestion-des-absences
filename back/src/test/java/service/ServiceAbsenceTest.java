package service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.entite.Absence.TypeAbsence;
import dev.repository.RepositoryAbsence;
import dev.service.ServiceAbsence;

import static org.mockito.Mockito.*;

public class ServiceAbsenceTest {

	
	List<Absence> listeBdd = new ArrayList<>();
	private ServiceAbsence serviceAbsence;

	private RepositoryAbsence mockRepoAbsence;

	@Before
	public void init() {
		Absence db = new Absence();
		db.setMatriculeEmploye("M1");
		db.setDateDebut(LocalDate.of(2017, 05, 01));
		db.setDateFin(LocalDate.of(2017, 05, 04));
		db.setId(1);
		db.setMotif("Maladie youpi");
		db.setStatut(Statut.INITIALE);
		db.setType(TypeAbsence.RTT);
		
		
		
		listeBdd.add(db);
		
		Absence db2 = new Absence();
		db2.setMatriculeEmploye("M1");
		db2.setDateDebut(LocalDate.of(2017, 11, 06));
		db2.setDateFin(LocalDate.of(2017, 11, 24));
		db2.setMotif("Je galère... :-(");
		db2.setId(7);
		db2.setStatut(Statut.VALIDEE);
		db2.setType(TypeAbsence.MISSION);

		listeBdd.add(db2);
		
		
		mockRepoAbsence = mock(RepositoryAbsence.class);
		serviceAbsence = new ServiceAbsence(mockRepoAbsence);
		

	}

	

	@Test
	public void verificationChevaucheTest() {
		Absence absence = new Absence();
		absence.setMatriculeEmploye("M1");
		
		when(mockRepoAbsence.findByMatriculeEmploye("M1")).thenReturn(listeBdd);
		/**
		 * M1 have 2 absences programmed 2017-05-01 -> 2017-05-04 
		 * 2017-11-06 ->2017-11-24
		 */

		// chevauche
		absence.setDateDebut(LocalDate.of(2017, 05, 02));
		absence.setDateFin(LocalDate.of(2017, 05, 03));
		
		boolean result = serviceAbsence.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);

		// chevauche2
		absence.setDateDebut(LocalDate.of(2017, 04, 30));
		absence.setDateFin(LocalDate.of(2017, 05, 24));

		result = serviceAbsence.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);

		// chevauche3
		absence.setDateDebut(LocalDate.of(2017, 05, 01));
		absence.setDateFin(LocalDate.of(2017, 05, 04));

		result = serviceAbsence.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);

		// partialChevauche1
		absence.setDateDebut(LocalDate.of(2017, 05, 02));
		absence.setDateFin(LocalDate.of(2017, 05, 03));

		result = serviceAbsence.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);

		// partialChevauche2
		absence.setDateDebut(LocalDate.of(2017, 05, 03));
		absence.setDateFin(LocalDate.of(2017, 05, 10));

		result = serviceAbsence.verificationChevauche(absence);
		assertThat(result).isEqualTo(false);

		// pas de chevauche1 after
		absence.setDateDebut(LocalDate.of(2017, 12, 01));
		absence.setDateFin(LocalDate.of(2017, 12, 04));

		result = serviceAbsence.verificationChevauche(absence);
		assertThat(result).isEqualTo(true);

		// pas de chevauche2 before
		absence.setDateDebut(LocalDate.of(2017, 10, 01));
		absence.setDateFin(LocalDate.of(2017, 10, 04));

		result = serviceAbsence.verificationChevauche(absence);
		assertThat(result).isEqualTo(true);

		// is the same absence
		// partialChevauche1
		absence.setId(1);
		absence.setDateDebut(LocalDate.of(2017, 05, 02));
		absence.setDateFin(LocalDate.of(2017, 05, 03));

		result = serviceAbsence.verificationChevauche(absence);
		assertThat(result).isEqualTo(true);
		

	}


	@Test
	public void conditionsTest() {
		Absence absence = new Absence();
		when(mockRepoAbsence.findByMatriculeEmploye("M1")).thenReturn(listeBdd);
		// CORRECT
		// pas de chevauche1 after
		LocalDate init = LocalDate.of(2017, 12, 01);
		LocalDate fin = LocalDate.of(2017, 12, 04);
		absence.setDateDebut(init);
		absence.setDateFin(fin);
		String matricule = "M1";
		absence.setMatriculeEmploye(matricule);
		

		absence.setType(TypeAbsence.CONGES_PAYES);
		// assertThat(absence.getType()!= TypeAbsence.MISSION).isEqualTo(true);
		// assertThat(!absence.getDateDebut().isBefore(LocalDate.now())).isEqualTo(true);
		// assertThat(!absence.getDateDebut().isEqual(LocalDate.now())).isEqualTo(true);

		// le motif c'est pas necessaire pour conge paye

		boolean result = serviceAbsence.conditions(absence);
		assertThat(result).isEqualTo(true);

		// test mission il donne faux
		absence.setType(TypeAbsence.MISSION);
		result = serviceAbsence.conditions(absence);
		assertThat(result).isEqualTo(false);

		absence.setType(TypeAbsence.CONGES_SANS_SOLDE);

		// test motifNull = false
		result = serviceAbsence.conditions(absence);
		assertThat(result).isEqualTo(false);

		// test motifNull = false
		absence.setMotif("xxx");
		result = serviceAbsence.conditions(absence);
		assertThat(result).isEqualTo(true);

		// incorrect dates - fin before init
		absence.setDateDebut(fin);
		absence.setDateFin(init);
		result = serviceAbsence.conditions(absence);
		assertThat(result).isEqualTo(false);

		// incorrect dates -start now
		absence.setDateDebut(LocalDate.now());
		result = serviceAbsence.conditions(absence);
		assertThat(result).isEqualTo(false);

		// incorrect dates - start in the past
		absence.setDateDebut(LocalDate.of(2016, 01, 01));
		result = serviceAbsence.conditions(absence);
		assertThat(result).isEqualTo(false);

	}
	

}

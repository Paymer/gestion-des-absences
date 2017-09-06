package service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.App;
import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.entite.Absence.TypeAbsence;
import dev.entite.Collaborateur;
import dev.repository.RepositoryAbsence;
import dev.repository.RepositoryMessageErreur;
import dev.service.ServiceCollaborateur;
import dev.service.ServiceTraitementNuit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { App.class })
public class ServiceTraitementNuitTest {

	@Autowired
	private ServiceTraitementNuit stn;
	@Autowired
	private RepositoryAbsence ra;
	@Autowired
	private ServiceCollaborateur sc;
	@Autowired
	private RepositoryMessageErreur rme;
	
	@Before
	public void init(){
		ra.deleteAll();
		rme.deleteAll();
	}

	@Test
	public void notNull() {
		assertThat(stn).isNotNull();
	}

	@Test
	public void passerNuit() {
		assertThat(stn.passerNuit()).isTrue();
	}
	
	@Test
	public void gererAbsenceInvalide(){
		Absence a = new Absence();
		a.setMatriculeEmploye("test");
		ra.save(a);
		
		stn.passerNuit();
		
		assertThat(rme.findAll().size()).isEqualTo(1);
	}

	@Test
	public void gererAbsenceCongesPayes() {
		Absence a = new Absence();
		Collaborateur c = sc.findAll().get(0);
		c.setCongesPayes(11);
		a.setDateDebut(LocalDate.now());
		a.setDateFin(LocalDate.now().plusDays(1));
		a.setMatriculeEmploye(c.getMatricule());
		a.setMotif("un Motif");
		a.setType(TypeAbsence.CONGE_PAYE);
		a.setStatut(Statut.INITIALE);
		ra.save(a);
		
		stn.passerNuit();
		
		a = ra.findByMatriculeEmploye(c.getMatricule()).get(0);
		c = sc.findAll().get(0);
		assertThat(a.getStatut()).isEqualTo(Statut.EN_ATTENTE_VALIDATION);
		assertThat(c.getCongesPayes()).isEqualTo(10);
	}

	@Test
	public void gererAbsenceCongesPayesInvalide() {
		Absence a = new Absence();
		Collaborateur c = sc.findAll().get(0);
		c.setCongesPayes(0);
		a.setDateDebut(LocalDate.now());
		a.setDateFin(LocalDate.now().plusDays(1));
		a.setMatriculeEmploye(c.getMatricule());
		a.setMotif("un Motif");
		a.setType(TypeAbsence.CONGE_PAYE);
		a.setStatut(Statut.INITIALE);
		ra.save(a);
		
		stn.passerNuit();
		
		a = ra.findByMatriculeEmploye(c.getMatricule()).get(0);
		c = sc.findAll().get(0);
		assertThat(a.getStatut()).isEqualTo(Statut.REJETEE);
		assertThat(c.getCongesPayes()).isEqualTo(0);
	}

	@Test
	public void gererAbsenceRtt() {
		Absence a = new Absence();
		Collaborateur c = sc.findAll().get(0);
		c.setRtt(11);
		a.setDateDebut(LocalDate.now());
		a.setDateFin(LocalDate.now().plusDays(1));
		a.setMatriculeEmploye(c.getMatricule());
		a.setMotif("un Motif");
		a.setType(TypeAbsence.RTT);
		a.setStatut(Statut.INITIALE);
		ra.save(a);
		
		stn.passerNuit();
		
		a = ra.findByMatriculeEmploye(c.getMatricule()).get(0);
		c = sc.findAll().get(0);
		assertThat(a.getStatut()).isEqualTo(Statut.EN_ATTENTE_VALIDATION);
		assertThat(c.getRtt()).isEqualTo(10);
	}

	@Test
	public void gererAbsenceRttInvalide() {
		Absence a = new Absence();
		Collaborateur c = sc.findAll().get(0);
		c.setRtt(0);
		a.setDateDebut(LocalDate.now());
		a.setDateFin(LocalDate.now().plusDays(1));
		a.setMatriculeEmploye(c.getMatricule());
		a.setMotif("un Motif");
		a.setType(TypeAbsence.RTT);
		a.setStatut(Statut.INITIALE);
		ra.save(a);
		
		stn.passerNuit();
		
		a = ra.findByMatriculeEmploye(c.getMatricule()).get(0);
		c = sc.findAll().get(0);
		assertThat(a.getStatut()).isEqualTo(Statut.REJETEE);
		assertThat(c.getRtt()).isEqualTo(0);
	}

	@Test
	public void gererAbsenceRttEmployeur() {
		Absence a = new Absence();
		Collaborateur c = sc.findAll().get(0);
		c.setRtt(11);
		a.setDateDebut(LocalDate.now());
		a.setDateFin(LocalDate.now().plusDays(1));
		a.setMatriculeEmploye(c.getMatricule());
		a.setMotif("un Motif");
		a.setType(TypeAbsence.RTT_EMPLOYEUR);
		a.setStatut(Statut.INITIALE);
		ra.save(a);
		
		stn.passerNuit();
		
		a = ra.findAll().get(0);
		c = sc.findCollaborateurParMatricule(c.getMatricule()).get();
		assertThat(a.getStatut()).isEqualTo(Statut.VALIDEE);
		assertThat(c.getRtt()).isEqualTo(10);
	}

	@Test
	public void gererAbsenceRttEmployeurEmployeSansRtt() {
		Absence a = new Absence();
		Collaborateur c = sc.findAll().get(0);
		c.setRtt(0);
		a.setDateDebut(LocalDate.now());
		a.setDateFin(LocalDate.now().plusDays(1));
		a.setMatriculeEmploye(c.getMatricule());
		a.setMotif("un Motif");
		a.setType(TypeAbsence.RTT_EMPLOYEUR);
		a.setStatut(Statut.INITIALE);
		ra.save(a);
		
		stn.passerNuit();
		
		a = ra.findAll().get(0);
		c = sc.findAll().get(0);
		assertThat(a.getStatut()).isEqualTo(Statut.VALIDEE);
		assertThat(c.getRtt()).isEqualTo(0);
		assertThat(rme.findAll().size()).isEqualTo(1);
	}

}

package dev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.entite.Absence.TypeAbsence;
import dev.entite.Collaborateur;
import dev.entite.MessageErreur;
import dev.repository.RepositoryAbsence;
import dev.repository.RepositoryMessageErreur;

@Service
public class ServiceAbsence {

	@Autowired
	private ServiceCollaborateur collabs;
	@Autowired
	private RepositoryMessageErreur messages;
	private String service = "Service des Absences";

	private RepositoryAbsence repoAbsence;

	@Autowired
	public ServiceAbsence(RepositoryAbsence repoAbsence) {
		super();
		this.repoAbsence = repoAbsence;
	}

	/** method qui va a reviser toutes les conditions pour faire le post */
	public boolean conditions(Absence newAbsence) {

		LocalDate t = LocalDate.now();

		boolean notMission = (newAbsence.getType() != TypeAbsence.MISSION);
		boolean dates = newAbsence.getDateDebut().isBefore(newAbsence.getDateFin());
		boolean dateInit = !newAbsence.getDateDebut().isBefore(t);
		boolean motif;

		if (newAbsence.getType() == TypeAbsence.CONGES_SANS_SOLDE && newAbsence.getMotif() == null) {
			motif = false;
		} else {
			motif = true;
		}

		boolean chevauche = verificationChevauche(newAbsence);

		return (notMission && dates && dateInit && motif && chevauche);

	}

	// check du chevauchement des dates
	public boolean verificationChevauche(Absence newAbsence) {

		List<Absence> liste = repoAbsence.findByMatriculeEmploye(newAbsence.getMatriculeEmploye());

		for (Absence a : liste) {
			boolean ch1 = a.getDateFin().isBefore(newAbsence.getDateDebut()); // the
																				// before
			boolean ch2 = a.getDateDebut().isAfter(newAbsence.getDateFin()); // the
																				// after
			boolean ch3 = a.getId().equals(newAbsence.getId()); // is the same

			if (!ch1 && !ch2 && !ch3) {
				return false;
			}
		}
		return true;
	}

	public boolean validationDemande(Collaborateur validateur, int idAbsence, boolean statut) {
		Absence a = repoAbsence.findOne(idAbsence);
		boolean retour = false;

		MessageErreur msg = new MessageErreur();
		msg.setServiceOrigine(service);
		msg.setMessage("Could not validate absence " + idAbsence + " : ");

		if (a.getStatut() == Statut.EN_ATTENTE_VALIDATION) {
			Optional<Collaborateur> demandeur = collabs.findCollaborateurParMatricule(a.getMatriculeEmploye());
			if (demandeur.isPresent() && demandeur.get().getManager().isPresent()) {
				if (demandeur.get().getManager().get().getMatricule().equals(validateur.getMatricule())) {
					Statut s = (statut) ? Statut.VALIDEE : Statut.REJETEE;
					a.setStatut(s);
					repoAbsence.save(a);
					retour = true;
				} else {
					msg.setMessage(
							msg.getMessage() + "\n" + validateur.getMatricule() + "\nemployee is not in his team !");
					messages.save(msg);
				}

			} else {
				msg.setMessage(msg.getMessage() + "\nasker is invalid or have no manager!");
			}

		} else {
			msg.setMessage(msg.getMessage() + "\nabsence is not in ATTENTE_DE_VALIDATION!");
		}
		return retour;

	}

	public List<Absence> getAbsencesPourManager(String matriculeManager) {
		return this.getAbsencesNormales().stream().filter(a -> {
			Optional<Collaborateur> oc = collabs.findCollaborateurParMatricule(a.getMatriculeEmploye());
			if (oc.isPresent()) {
				Collaborateur c = oc.get();
				Optional<Collaborateur> ocmanager = c.getManager();
				if (ocmanager.isPresent() && ocmanager.get().getMatricule().equals(matriculeManager) && a.getStatut()==Statut.EN_ATTENTE_VALIDATION) {
					return true;
				}
			}
			return false;
		}).collect(Collectors.toList());
	}

	public List<Absence> getRttEmployeur() {
		return this.getJoursFeriesEtRttEmployeurs().stream().filter(abs -> abs.getType() == TypeAbsence.RTT_EMPLOYEUR)
				.collect(Collectors.toList());
	}

	public List<Absence> getAbsencesNormales() {
		return repoAbsence.findAll().stream()
				.filter(abs -> abs.getType() != TypeAbsence.RTT_EMPLOYEUR && abs.getType() != TypeAbsence.JOUR_FERIE)
				.collect(Collectors.toList());
	}

	public List<Absence> getJoursFeriesEtRttEmployeurs() {
		return repoAbsence.findAll().stream()
				.filter(abs -> abs.getType() == TypeAbsence.RTT_EMPLOYEUR || abs.getType() == TypeAbsence.JOUR_FERIE)
				.sorted((a1, a2) -> a2.getDateDebut().compareTo(a1.getDateDebut())).collect(Collectors.toList());
	}

	public boolean isJourFerieLibre(LocalDate date) {
		return !this.getJoursFeriesEtRttEmployeurs().stream().filter(jf -> jf.getDateDebut().isEqual(date)).findAny()
				.isPresent();
	}

}

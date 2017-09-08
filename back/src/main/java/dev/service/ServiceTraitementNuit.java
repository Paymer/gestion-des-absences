package dev.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.entite.Collaborateur;
import dev.entite.MessageErreur;
import dev.repository.RepositoryAbsence;
import dev.repository.RepositoryMessageErreur;

@Service
public class ServiceTraitementNuit {

	@Autowired
	private RepositoryAbsence repoAbsences;
	@Autowired
	private ServiceCollaborateur servCollaborateurs;
	@Autowired
	private RepositoryMessageErreur repoMessageErreur;
	
	private String service = "Traitement de Nuit";
	
	@Scheduled(cron = "0 */1 * * * *")
	public Boolean passerNuit() {
		List<Absence> listeAbsences = repoAbsences.findAll();
		listeAbsences.forEach(this::gererAbsence);
		return true;
	}

	private void gererAbsence(Absence absence) {
		Optional<Collaborateur> oc = servCollaborateurs.findCollaborateurParMatricule(absence.getMatriculeEmploye());
		if (oc.isPresent()) {
			Collaborateur collaborateurCourant = oc.get();

			Statut statut = Statut.EN_ATTENTE_VALIDATION;
			int nbJours = (int) (java.time.temporal.ChronoUnit.DAYS.between(absence.getDateDebut(),
					absence.getDateFin()));

			switch (absence.getType()) {
			case CONGES_PAYES:
				if (collaborateurCourant.getCongesPayes() < nbJours) {
					statut = Statut.REJETEE;
				} else {
					collaborateurCourant.setCongesPayes(collaborateurCourant.getCongesPayes() - nbJours);
				}
				break;
			case RTT:
				if (collaborateurCourant.getRtt() < nbJours) {
					statut = Statut.REJETEE;
				} else {
					collaborateurCourant.setRtt(collaborateurCourant.getRtt() - nbJours);
				}
				break;
			case RTT_EMPLOYEUR:
				statut = Statut.VALIDEE;
				this.creerRttEmployeur(nbJours);
				break;
			default:
				break;
			}
			absence.setStatut(statut);
			repoAbsences.save(absence);

		} else {
			String message = "Une absence était invalide : " + absence.getId() + " - Du " + absence.getDateDebut()
					+ " au " + absence.getDateFin();
			this.creerMessage(message);
			repoAbsences.delete(absence);
		}
	}

	private void creerRttEmployeur(int nbJours) {
		List<Collaborateur> collabsErreur = new ArrayList<>();
		List<Collaborateur> collaborateurs = servCollaborateurs.findAll();
		for (Collaborateur c : collaborateurs) {
			if (c.getRtt() > 0) {
				c.setRtt(c.getRtt() - nbJours);
			} else {
				collabsErreur.add(c);
			}
		}
		if (!collabsErreur.isEmpty()) {
			StringBuilder message = new StringBuilder();
			message.append("Un ou plusieurs employé n'avaient plus de RTT disponible :");
			for (Collaborateur c : collabsErreur) {
				message.append("\n" + c.getMatricule() + " - " + c.getNom() + " " + c.getPrenom());
			}
			this.creerMessage(message.toString());
		}
	}

	private void creerMessage(String message) {
		MessageErreur msg = new MessageErreur();
		msg.setServiceOrigine(service);
		msg.setMessage(message);
		repoMessageErreur.save(msg);
	}

}

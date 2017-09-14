package dev.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class ServiceTraitementNuit {

	@Autowired
	private RepositoryAbsence repoAbsences;
	@Autowired
	private ServiceCollaborateur servCollaborateurs;
	@Autowired
	private RepositoryMessageErreur repoMessageErreur;
	
	private String service = "Traitement de Nuit";
	
	//@Scheduled(cron = "0 0 0 * * *")
	public Boolean passerNuit() {
		repoAbsences.findAll().forEach(a -> {
			if(a.getStatut() == Statut.INITIALE){
				this.gererAbsence(a);
			}
		});
		return true;
	}

	private void gererAbsence(Absence absence) {
		Optional<Collaborateur> oc = servCollaborateurs.findCollaborateurParMatricule(absence.getMatriculeEmploye());
		if (oc.isPresent()) {
			Collaborateur collaborateurCourant = oc.get();

			Statut statut = Statut.EN_ATTENTE_VALIDATION;
			int nbJours = (int) (java.time.temporal.ChronoUnit.DAYS.between(absence.getDateDebut(),
					absence.getDateFin()));

			if(collaborateurCourant.getMatricule().equals("a8fc21fc")){
				collaborateurCourant.toString();
			}
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
			default:
				break;
			}
			absence.setStatut(statut);
			repoAbsences.save(absence);

		} else if(absence.getType()==TypeAbsence.RTT_EMPLOYEUR){
			this.creerRttEmployeur(1);
			absence.setStatut(Statut.VALIDEE);
			repoAbsences.save(absence);
		}else if(absence.getType()!=TypeAbsence.JOUR_FERIE){
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
			if (c.getRtt() >= nbJours) {
				c.setRtt(c.getRtt() - nbJours);
			} else {
				c.setRtt(0);
				collabsErreur.add(c);
			}
		}
		if (!collabsErreur.isEmpty()) {
			StringBuilder message = new StringBuilder();
			message.append("RTT employeur - Un ou plusieurs employé n'avaient plus de RTT disponible :");
			for (Collaborateur c : collabsErreur) {
				message.append("\n" + c.getNom() + " " + c.getPrenom());
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

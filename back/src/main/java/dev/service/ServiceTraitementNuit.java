package dev.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import dev.entite.Absence;
import dev.entite.Absence.Statut;
import dev.entite.Collaborateur;
import dev.entite.MessageErreur;
import dev.repository.RepositoryAbsence;
import dev.repository.RepositoryMessageErreur;

public class ServiceTraitementNuit {

	@Autowired
	private RepositoryAbsence repoAbsences;
	@Autowired
	private ServiceCollaborateur servCollaborateurs;
	@Autowired
	private RepositoryMessageErreur repoMessageErreur;

	public void passerNuit() {
		List<Absence> listeAbsences = repoAbsences.findAll();
		listeAbsences.forEach(this::gererAbsence);
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
				List<Collaborateur> collabsErreur = new ArrayList<>();
				List<Collaborateur> collaborateurs = servCollaborateurs.findAll();
				for(Collaborateur c : collaborateurs) {
					if(c.getRtt()>0){
						c.setRtt(c.getRtt()-1);
					}else{
						collabsErreur.add(c);
					}
				}
				if(!collabsErreur.isEmpty()){
					String service = "Traitement de Nuit";
					StringBuilder message = new StringBuilder();
					message.append("Un ou plusieurs employé n'avaient plus de RTT disponible :");
					for(Collaborateur c : collabsErreur){
						message.append("\n"+c.getMatricule()+" - "+c.getNom()+" "+c.getPrenom());
					}
					MessageErreur msg = new MessageErreur();
					msg.setServiceOrigine(service);
					msg.setMessage(message.toString());
					repoMessageErreur.save(msg);
				}
				break;
			default:
				break;
			}
			absence.setStatut(statut);

		} else {
			repoAbsences.delete(absence);
		}
	}

}

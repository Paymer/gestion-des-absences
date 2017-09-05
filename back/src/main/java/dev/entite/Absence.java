package dev.entite;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "absence")
public class Absence {
	
	public enum TypeAbsence {
		CONGE_PAYE, RTT, CONGE_SANS_SOLDE;
	}
	
	public enum Statut {
		INITIALE, EN_ATTENTE_VALIDATION, VALIDEE, REJETEE;
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "date_debut")
	private LocalDate dateDebut;
	@Column(name = "date_fin")
	private LocalDate dateFin;
	@Enumerated(EnumType.STRING)
	private TypeAbsence type;
	
	private Integer idEmploye;
	private String motif;
	private Statut statut;
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public TypeAbsence getType() {
		return type;
	}

	public void setType(TypeAbsence type) {
		this.type = type;
	}

	public Integer getIdEmploye() {
		return idEmploye;
	}

	public void setIdEmploye(Integer idEmploye) {
		this.idEmploye = idEmploye;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}
	
	
}

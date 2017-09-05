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
		CONGE_PAYE, RTT, CONGE_SANS_SOLDE, MISSION;
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
	@Column(name = "id_employe")
	private Integer idEmploye;
	private String motif;
	@Enumerated(EnumType.STRING)
	private Statut statut;
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}
	/**
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}
	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
	/**
	 * @return the type
	 */
	public TypeAbsence getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(TypeAbsence type) {
		this.type = type;
	}
	/**
	 * @return the idEmploye
	 */
	public Integer getIdEmploye() {
		return idEmploye;
	}
	/**
	 * @param idEmploye the idEmploye to set
	 */
	public void setIdEmploye(Integer idEmploye) {
		this.idEmploye = idEmploye;
	}
	/**
	 * @return the motif
	 */
	public String getMotif() {
		return motif;
	}
	/**
	 * @param motif the motif to set
	 */
	public void setMotif(String motif) {
		this.motif = motif;
	}
	/**
	 * @return the statut
	 */
	public Statut getStatut() {
		return statut;
	}
	/**
	 * @param statut the statut to set
	 */
	public void setStatut(Statut statut) {
		this.statut = statut;
	}
	
	
	
}

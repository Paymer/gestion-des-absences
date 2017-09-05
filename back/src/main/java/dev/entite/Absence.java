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
		TEST, TESTOU, PLOUFEUH; // TODO à changer
	}
	
	public enum Statut {
		INITIAL, PWET, PWETOU, PLOUF; // TODO à changer
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "date-debut")
	private LocalDate dateDebut;
	@Column(name = "date-fin")
	private LocalDate dateFin;
	@Enumerated(EnumType.STRING)
	private TypeAbsence type;
	
	private Integer idEmploye;
	private String motif;
	private Statut statut;
	
	
	
}

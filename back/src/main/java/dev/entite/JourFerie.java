package dev.entite;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "jour-ferie")
public class JourFerie {
	
	public enum TypeJourFerie {
		INITIAL, PWET, PWETOU, PLOUF; // TODO à changer
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDate date; //CONFLICT POSSIBLE PAR PAM
	private TypeJourFerie type;
	private String commentaire ;
	
}

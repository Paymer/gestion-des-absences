package dev.entite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Collaborateur {
	
	public enum Grade {
		EMPLOYE, MANAGER, ADMINISTRATEUR;
	}

	private static final int BASE_CONGES_PAYES = 28;
	private static final int BASE_RTT = 11;
	
	@Id
	private String matricule;
	@Transient
	private String nom;
	@Transient
	private String prenom;
	@Transient
	private String email;
	@Transient
	private String password;
	@Transient
	private Optional<Collaborateur> manager = Optional.ofNullable(null);
	@Transient
	private List<Collaborateur> subalternes = new ArrayList<>();
	@Transient
	private Departement departement;
	private Grade grade = Grade.EMPLOYE;
	@Column(name="conges_payes")
	private int congesPayes = BASE_CONGES_PAYES;
	private int rtt = BASE_RTT;

	public Collaborateur() {
		super();
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public Optional<Collaborateur> getManager() {
		return this.manager;
	}

	public void setManager(Collaborateur manager) {
		this.manager = Optional.ofNullable(manager);
	}
	
	public List<Collaborateur> getSubalternes(){
		return new ArrayList<>(this.subalternes);
	}
	
	public void addSubalterne(Collaborateur sub){
		this.subalternes.add(sub);
	}
	
	public Grade getGrade(){
		return this.grade;
	}
	
	public void setGrade(Grade grade){
		this.grade = grade;
	}

	public int getCongesPayes() {
		return congesPayes;
	}

	public void setCongesPayes(int congesPayes) {
		this.congesPayes = congesPayes;
	}

	public int getRtt() {
		return rtt;
	}

	public void setRtt(int rtt) {
		this.rtt = rtt;
	}

}

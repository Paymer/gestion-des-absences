package dev.entite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Collaborateur {
	
	public enum Grade {
		Employé, Manager, Administrateur;
	}

	private String matricule;
	private String nom;
	private String prenom;
	private String email;
	private String password;
	private Optional<Collaborateur> manager = Optional.ofNullable(null);
	private List<Collaborateur> subalternes = new ArrayList<>();
	private Departement departement;
	private Grade grade = Grade.Employé;

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
}

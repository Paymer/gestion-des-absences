package dev.entite;

import java.util.List;

public class Collaborateur {
	
	private String matricule;
	private String nom;
	private String prenom;
	private String email;
	private String password;
	private List<Collaborateur> subalternes;
	private Departement departement;
	
	public Collaborateur() {
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

	public List<Collaborateur> getSubalternes() {
		return subalternes;
	}

	public void setSubalternes(List<Collaborateur> subalternes) {
		this.subalternes = subalternes;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

}

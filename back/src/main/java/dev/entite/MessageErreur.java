package dev.entite;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "message_erreur")
public class MessageErreur {

	@Id
	private int id;
	private LocalDate date;
	@Column(name = "service_origine")
	private String serviceOrigine;
	private String message;

	public MessageErreur() {
		super();
		this.date = LocalDate.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServiceOrigine() {
		return serviceOrigine;
	}

	public void setServiceOrigine(String serviceOrigine) {
		this.serviceOrigine = serviceOrigine;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}

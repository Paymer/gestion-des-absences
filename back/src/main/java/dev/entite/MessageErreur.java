package dev.entite;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "message_erreur")
public class MessageErreur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime date;
	@Column(name = "service_origine")
	private String serviceOrigine;
	private String message;

	public MessageErreur() {
		super();
		this.date = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}

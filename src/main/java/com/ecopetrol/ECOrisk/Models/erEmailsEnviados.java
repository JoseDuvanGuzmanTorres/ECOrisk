package com.ecopetrol.ECOrisk.Models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class erEmailsEnviados {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_email_id;
	
	@ManyToOne
	@JoinColumn(name="user_id",insertable = false, updatable = false)
	private Users usuario;
	private Integer user_id;
	
	private String asunto;
	
	private Date fecha;

	public int getEr_email_id() {
		return er_email_id;
	}

	public void setEr_email_id(int er_email_id) {
		this.er_email_id = er_email_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
}

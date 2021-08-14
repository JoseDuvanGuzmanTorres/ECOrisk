package com.ecopetrol.ECOrisk.Models;

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
public class erParticipantes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_participante_id;
	
	@ManyToOne
	@JoinColumn(name="er_encabezado_id",insertable = false, updatable = false)
	private erEncabezado encabezado;
	private Integer er_encabezado_id;
	
	@ManyToOne
	@JoinColumn(name="er_participante",insertable = false, updatable = false)
	private Users user;
	private Integer er_participante;
	
	private String er_dependencia;
	
	private String er_rol;

	public int getEr_participante_id() {
		return er_participante_id;
	}

	public void setEr_participante_id(int er_participante_id) {
		this.er_participante_id = er_participante_id;
	}

	public Integer getEr_encabezado_id() {
		return er_encabezado_id;
	}

	public void setEr_encabezado_id(Integer er_encabezado_id) {
		this.er_encabezado_id = er_encabezado_id;
	}

	public Integer getEr_participante() {
		return er_participante;
	}

	public void setEr_participante(Integer er_participante) {
		this.er_participante = er_participante;
	}

	public String getEr_dependencia() {
		return er_dependencia;
	}

	public void setEr_dependencia(String er_dependencia) {
		this.er_dependencia = er_dependencia;
	}

	public String getEr_rol() {
		return er_rol;
	}

	public void setEr_rol(String er_rol) {
		this.er_rol = er_rol;
	}
	
	
	
}

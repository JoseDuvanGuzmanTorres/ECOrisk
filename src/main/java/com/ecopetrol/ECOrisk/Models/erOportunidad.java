package com.ecopetrol.ECOrisk.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class erOportunidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_oportunidad_id;
	
	private String er_oportunidad;

	public int getEr_oportunidad_id() {
		return er_oportunidad_id;
	}

	public void setEr_oportunidad_id(int er_oportunidad_id) {
		this.er_oportunidad_id = er_oportunidad_id;
	}

	public String getEr_oportunidad() {
		return er_oportunidad;
	}

	public void setEr_oportunidad(String er_oportunidad) {
		this.er_oportunidad = er_oportunidad;
	}

	
}

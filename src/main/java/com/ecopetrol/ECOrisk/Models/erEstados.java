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
public class erEstados {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_estado_id;
	
	private String er_estado;

	public int getEr_estado_id() {
		return er_estado_id;
	}

	public void setEr_estado_id(int er_estado_id) {
		this.er_estado_id = er_estado_id;
	}

	public String getEr_estado() {
		return er_estado;
	}

	public void setEr_estado(String er_estado) {
		this.er_estado = er_estado;
	}
	
}

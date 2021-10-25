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
public class erEstadopro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_estadofeproyecto_id;
	
	private String estadopro;

	public int getEr_estadofeproyecto_id() {
		return er_estadofeproyecto_id;
	}

	public void setEr_estadofeproyecto_id(int er_estadofeproyecto_id) {
		this.er_estadofeproyecto_id = er_estadofeproyecto_id;
	}

	public String getEstadopro() {
		return estadopro;
	}

	public void setEstadopro(String estadopro) {
		this.estadopro = estadopro;
	}

	
}

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
public class erEtapas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ee_etapaproyecto_id;
	
	private String ee_etapaproyecto;

	public int getEe_etapaproyecto_id() {
		return ee_etapaproyecto_id;
	}

	public void setEe_etapaproyecto_id(int ee_etapaproyecto_id) {
		this.ee_etapaproyecto_id = ee_etapaproyecto_id;
	}

	public String getEe_etapaproyecto() {
		return ee_etapaproyecto;
	}

	public void setEe_etapaproyecto(String ee_etapaproyecto) {
		this.ee_etapaproyecto = ee_etapaproyecto;
	}
	
	

}

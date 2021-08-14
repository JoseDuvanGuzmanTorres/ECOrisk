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
public class erTipoEstudio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_tipoestudio_id;
	
	private String er_tipoestudio;

	public int getEr_tipoestudio_id() {
		return er_tipoestudio_id;
	}

	public void setEr_tipoestudio_id(int er_tipoestudio_id) {
		this.er_tipoestudio_id = er_tipoestudio_id;
	}

	public String getEr_tipoestudio() {
		return er_tipoestudio;
	}

	public void setEr_tipoestudio(String er_tipoestudio) {
		this.er_tipoestudio = er_tipoestudio;
	}

	
}

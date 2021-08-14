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
public class erProyecto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_proyecto_id;
	
	private String er_proyecto;

	private String er_proyecto_nombre;
	
	public int getEr_proyecto_id() {
		return er_proyecto_id;
	}

	public void setEr_proyecto_id(int er_proyecto_id) {
		this.er_proyecto_id = er_proyecto_id;
	}

	public String getEr_proyecto() {
		return er_proyecto;
	}

	public void setEr_proyecto(String er_proyecto) {
		this.er_proyecto = er_proyecto;
	}

	public String getEr_proyecto_nombre() {
		return er_proyecto_nombre;
	}

	public void setEr_proyecto_nombre(String er_proyecto_nombre) {
		this.er_proyecto_nombre = er_proyecto_nombre;
	}
	
	
}

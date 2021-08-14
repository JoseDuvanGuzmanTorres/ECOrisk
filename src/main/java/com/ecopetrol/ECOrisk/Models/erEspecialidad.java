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
public class erEspecialidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int es_especialidad_id;
	
	private String es_especialidad;

	public int getEs_especialidad_id() {
		return es_especialidad_id;
	}

	public void setEs_especialidad_id(int es_especialidad_id) {
		this.es_especialidad_id = es_especialidad_id;
	}

	public String getEs_especialidad() {
		return es_especialidad;
	}

	public void setEs_especialidad(String es_especialidad) {
		this.es_especialidad = es_especialidad;
	}

	
	
}

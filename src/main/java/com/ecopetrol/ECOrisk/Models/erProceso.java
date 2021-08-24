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
public class erProceso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_proceso_id;
	
	private String er_proceso;

	public int getEr_proceso_id() {
		return er_proceso_id;
	}

	public void setEr_proceso_id(int er_proceso_id) {
		this.er_proceso_id = er_proceso_id;
	}

	public String getEr_proceso() {
		return er_proceso;
	}

	public void setEr_proceso(String er_proceso) {
		this.er_proceso = er_proceso;
	}

	
}

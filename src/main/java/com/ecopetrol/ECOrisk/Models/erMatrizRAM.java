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
public class erMatrizRAM {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_matriz_id;
	
	private String er_matriz;

	public int getEr_matriz_id() {
		return er_matriz_id;
	}

	public void setEr_matriz_id(int er_matriz_id) {
		this.er_matriz_id = er_matriz_id;
	}

	public String getEr_matriz() {
		return er_matriz;
	}

	public void setEr_matriz(String er_matriz) {
		this.er_matriz = er_matriz;
	}

	
	
}

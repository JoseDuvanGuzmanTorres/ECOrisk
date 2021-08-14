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
public class erGerencias {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int eg_gerenciacliente_id;
	
	private String eg_gerenciacliente;

	public int getEg_gerenciacliente_id() {
		return eg_gerenciacliente_id;
	}

	public void setEg_gerenciacliente_id(int eg_gerenciacliente_id) {
		this.eg_gerenciacliente_id = eg_gerenciacliente_id;
	}

	public String getEg_gerenciacliente() {
		return eg_gerenciacliente;
	}

	public void setEg_gerenciacliente(String eg_gerenciacliente) {
		this.eg_gerenciacliente = eg_gerenciacliente;
	}
	
	

}

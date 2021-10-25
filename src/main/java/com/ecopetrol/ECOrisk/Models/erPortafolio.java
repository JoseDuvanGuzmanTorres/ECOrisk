package com.ecopetrol.ECOrisk.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class erPortafolio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_portafolio_id;
	
	
	private String er_nombre_corto;

	private String er_nombre_proyecto;
	private String estadop;
	private String fasep;
	private String er_mascara;
	
	@ManyToOne
	@JoinColumn(name="er_proceso_id",insertable = false, updatable = false)
	private erProceso proceso;
	private Integer er_proceso_id;

	public int getEr_portafolio_id() {
		return er_portafolio_id;
	}

	public void setEr_portafolio_id(int er_portafolio_id) {
		this.er_portafolio_id = er_portafolio_id;
	}

	public String getEr_nombre_corto() {
		return er_nombre_corto;
	}

	public void setEr_nombre_corto(String er_nombre_corto) {
		this.er_nombre_corto = er_nombre_corto;
	}


	public String getEstadop() {
		return estadop;
	}
	
	public void setEstadop(String estadop) {
		this.estadop = estadop;
	}


	public String getFasep() {
		return fasep;
	}
	
	public void setFasep(String fasep) {
		this.fasep = fasep;
	}
	
	public String getEr_nombre_proyecto() {
		return er_nombre_proyecto;
	}
	public void setEr_nombre_proyecto(String er_nombre_proyecto) {
		this.er_nombre_proyecto = er_nombre_proyecto;
	}

	public String getEr_mascara() {
		return er_mascara;
	}

	public void setEr_mascara(String er_mascara) {
		this.er_mascara = er_mascara;
	}

	public Integer getEr_proceso() {
		return er_proceso_id;
	}

	public void setEr_proceso(Integer er_proceso) {
		this.er_proceso_id = er_proceso;
	}
	
	
	
	/*@ManyToOne
	@JoinColumn(name="er_proyecto_id",insertable = false, updatable = false)
	private erProyecto proyecto;
	private Integer er_proyecto_id;*/
	
	
	
}

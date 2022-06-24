package com.ecopetrol.ECOrisk.Models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="er_infogeneraldetalleres")

public class erInfoGeneralTalleres {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_taller;
	private String estado_taller;
	
	public String getEstado_taller() {
		return estado_taller;
	}

	public void setEstado_taller(String estado_taller) {
		this.estado_taller = estado_taller;
	}

	public erInfoGeneralTalleres(String estado_taller) {
		super();
		this.estado_taller = estado_taller;
	}

	private String Estadotaller;
	private String Proyecto;
	private String Taller;
	private String Proceso;
	private String Ejecucion;
	private String Planeacion;
	private String Duenotaller;
	private String Lidertaller;
	private String Codigodocumento;
	

	
	public erInfoGeneralTalleres() {
		
	}

	public erInfoGeneralTalleres(int id_taller, String estadoTaller, String proyecto, String taller, String proceso,
			String ejecucion, String planeacion, String duenotaller, String lidertaller, String codigodocumento) {
		super();
		this.id_taller = id_taller;
		Estadotaller = estadoTaller;
		Proyecto = proyecto;
		Taller = taller;
		Proceso = proceso;
		Ejecucion = ejecucion;
		Planeacion = planeacion;
		Duenotaller = duenotaller;
		Lidertaller = lidertaller;
		Codigodocumento = codigodocumento;
	}

	public int getId_taller() {
		return id_taller;
	}

	public void setId_taller(int id_taller) {
		this.id_taller = id_taller;
	}

	public String getEstadoTaller() {
		return Estadotaller;
	}

	public void setEstadoTaller(String estadotaller) {
		Estadotaller = estadotaller;
	}

	public String getProyecto() {
		return Proyecto;
	}

	public void setProyecto(String proyecto) {
		Proyecto = proyecto;
	}

	public String getTaller() {
		return Taller;
	}

	public void setTaller(String taller) {
		Taller = taller;
	}

	public String getProceso() {
		return Proceso;
	}

	public void setProceso(String proceso) {
		Proceso = proceso;
	}

	public String getEjecucion() {
		return Ejecucion;
	}

	public void setEjecucion(String ejecucion) {
		Ejecucion = ejecucion;
	}

	public String getPlaneacion() {
		return Planeacion;
	}

	public void setPlaneacion(String planeacion) {
		Planeacion = planeacion;
	}

	public String getDuenotaller() {
		return Duenotaller;
	}

	public void setDuenotaller(String duenotaller) {
		Duenotaller = duenotaller;
	}

	public String getLidertaller() {
		return Lidertaller;
	}

	public void setLidertaller(String lidertaller) {
		Lidertaller = lidertaller;
	}

	public String getCodigodocumento() {
		return Codigodocumento;
	}

	public void setCodigodocumento(String codigodocumento) {
		Codigodocumento = codigodocumento;
	}

	@Override
	public String toString() {
		return "erInfoGeneralTalleres [id_taller=" + id_taller + ", EstadoTaller=" + Estadotaller + ", Proyecto="
				+ Proyecto + ", Taller=" + Taller + ", Proceso=" + Proceso + ", Ejecucion=" + Ejecucion
				+ ", Planeacion=" + Planeacion + ", Duenotaller=" + Duenotaller + ", Lidertaller=" + Lidertaller
				+ ", Codigodocumento=" + Codigodocumento + "]";
	}
	
	
}

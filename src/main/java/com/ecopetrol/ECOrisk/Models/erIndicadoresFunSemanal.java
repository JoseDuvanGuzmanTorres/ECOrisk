package com.ecopetrol.ECOrisk.Models;

import java.util.Date;

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
public class erIndicadoresFunSemanal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	Date fecha;
	
	@ManyToOne
	@JoinColumn(name="iddue",insertable = false, updatable = false)
	private Users user;
	private Integer iddue;

	Integer eventos;
	Integer riesgosvh;
	Integer riesgosh;
	Integer riesgosm;
	Integer riesgosl;
	Integer riesgostotal;
	Integer abiertostotal;
	Integer abiertosvencidos;
	Integer cerradostotal;
	Integer promgaps;
	Integer cumplimiento;
	Integer whatif;
	Integer constructabilidad;
	Integer dreview;
	Integer preview;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Integer getIddue() {
		return iddue;
	}
	public void setIddue(Integer iddue) {
		this.iddue = iddue;
	}
	public Integer getEventos() {
		return eventos;
	}
	public void setEventos(Integer eventos) {
		this.eventos = eventos;
	}
	public Integer getRiesgosvh() {
		return riesgosvh;
	}
	public void setRiesgosvh(Integer riesgosvh) {
		this.riesgosvh = riesgosvh;
	}
	public Integer getRiesgosh() {
		return riesgosh;
	}
	public void setRiesgosh(Integer riesgosh) {
		this.riesgosh = riesgosh;
	}
	public Integer getRiesgosm() {
		return riesgosm;
	}
	public void setRiesgosm(Integer riesgosm) {
		this.riesgosm = riesgosm;
	}
	public Integer getRiesgosl() {
		return riesgosl;
	}
	public void setRiesgosl(Integer riesgosl) {
		this.riesgosl = riesgosl;
	}
	public Integer getRiesgostotal() {
		return riesgostotal;
	}
	public void setRiesgostotal(Integer riesgostotal) {
		this.riesgostotal = riesgostotal;
	}
	public Integer getAbiertostotal() {
		return abiertostotal;
	}
	public void setAbiertostotal(Integer abiertostotal) {
		this.abiertostotal = abiertostotal;
	}
	public Integer getAbiertosvencidos() {
		return abiertosvencidos;
	}
	public void setAbiertosvencidos(Integer abiertosvencidos) {
		this.abiertosvencidos = abiertosvencidos;
	}
	public Integer getCerradostotal() {
		return cerradostotal;
	}
	public void setCerradostotal(Integer cerradostotal) {
		this.cerradostotal = cerradostotal;
	}
	public Integer getPromgaps() {
		return promgaps;
	}
	public void setPromgaps(Integer promgaps) {
		this.promgaps = promgaps;
	}
	public Integer getCumplimiento() {
		return cumplimiento;
	}
	public void setCumplimiento(Integer cumplimiento) {
		this.cumplimiento = cumplimiento;
	}
	public Integer getWhatif() {
		return whatif;
	}
	public void setWhatif(Integer whatif) {
		this.whatif = whatif;
	}
	public Integer getConstructabilidad() {
		return constructabilidad;
	}
	public void setConstructabilidad(Integer constructabilidad) {
		this.constructabilidad = constructabilidad;
	}
	public Integer getDreview() {
		return dreview;
	}
	public void setDreview(Integer dreview) {
		this.dreview = dreview;
	}
	public Integer getPreview() {
		return preview;
	}
	public void setPreview(Integer preview) {
		this.preview = preview;
	}
	
	
}

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
public class erEncabezado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_encabezado_id;
	
	@ManyToOne
	@JoinColumn(name="er_proyecto_id",insertable = false, updatable = false)
	private erProyecto proyecto;
	private Integer er_proyecto_id;
	
	@ManyToOne
	@JoinColumn(name="er_regionalgsc_id",insertable = false, updatable = false)
	private erRegionales regionales;
	private Integer er_regionalgsc_id;
	
	@ManyToOne
	@JoinColumn(name="eg_gerenciacliente_id",insertable = false, updatable = false)
	private erGerencias gerencias;
	private Integer eg_gerenciacliente_id;
	
	@ManyToOne
	@JoinColumn(name="ee_etapaproyecto_id",insertable = false, updatable = false)
	private erEtapas etapas;
	private Integer ee_etapaproyecto_id;
	
	@ManyToOne
	@JoinColumn(name="ed_deptocliente_id",insertable = false, updatable = false)
	private erDepartamentos departamentos;
	private Integer ed_deptocliente_id;
	
	@ManyToOne
	@JoinColumn(name="ed_coordcliente_id",insertable = false, updatable = false)
	private erDepartamentos departamentos1;
	private Integer ed_coordcliente_id;
	
	
	@ManyToOne
	@JoinColumn(name="ed_deptocliente_id2",insertable = false, updatable = false)
	private erDepartamentos departamentos2;
	private Integer ed_deptocliente_id2;
	
	@ManyToOne
	@JoinColumn(name="ed_deptocliente_id3",insertable = false, updatable = false)
	private erDepartamentos departamentos3;
	private Integer ed_deptocliente_id3;
	
	private String e_instalacioncliente;
	private String e_objetivoproyecto;
	private String e_nombreproyecto;
	private String e_codigodocumento;
	private Date e_fechataller;
	
	@ManyToOne
	@JoinColumn(name="e_lidertaller",insertable = false, updatable = false)
	private Users usuario;
	private Integer e_lidertaller;
	
	@ManyToOne
	@JoinColumn(name="e_liderproyecto",insertable = false, updatable = false)
	private Users usuario2;
	private Integer e_liderproyecto;

	private Date aud_fechacreacion;
	private Date e_fechainicio;
	private Date e_fechafin;
	
	@ManyToOne
	@JoinColumn(name="e_contacto",insertable = false, updatable = false)
	private Users contacto;
	private Integer e_contacto;
	
	@ManyToOne
	@JoinColumn(name="user_id",insertable = false, updatable = false)
	private Users users;
	private Integer user_id;
	
	@ManyToOne
	@JoinColumn(name="er_tipoestudio_id",insertable = false, updatable = false)
	private erTipoEstudio tipoestudio;
	private Integer er_tipoestudio_id;
	
	@ManyToOne
	@JoinColumn(name="er_proceso_id",insertable = false, updatable = false)
	private erProceso proceso;
	private Integer er_proceso_id;
	
	private Date e_fechacongelamiento;
	private Date e_fechadescongelamiento;
	
	public int getEr_encabezado_id() {
		return er_encabezado_id;
	}
	public void setEr_encabezado_id(int er_encabezado_id) {
		this.er_encabezado_id = er_encabezado_id;
	}
	
	public Integer getEr_proyecto_id() {
		return er_proyecto_id;
	}
	public void setEr_proyecto_id(Integer er_proyecto_id) {
		this.er_proyecto_id = er_proyecto_id;
	}
	public Integer getEr_regionalgsc_id() {
		return er_regionalgsc_id;
	}
	public void setEr_regionalgsc_id(Integer er_regionalgsc_id) {
		this.er_regionalgsc_id = er_regionalgsc_id;
	}
	public Integer getEg_gerenciacliente_id() {
		return eg_gerenciacliente_id;
	}
	public void setEg_gerenciacliente_id(Integer eg_gerenciacliente_id) {
		this.eg_gerenciacliente_id = eg_gerenciacliente_id;
	}
	public Integer getEe_etapaproyecto_id() {
		return ee_etapaproyecto_id;
	}
	public void setEe_etapaproyecto_id(Integer ee_etapaproyecto_id) {
		this.ee_etapaproyecto_id = ee_etapaproyecto_id;
	}
	public Integer getEd_deptocliente_id() {
		return ed_deptocliente_id;
	}
	public void setEd_deptocliente_id(Integer ed_deptocliente_id) { 
		this.ed_deptocliente_id = ed_deptocliente_id;
	}
	
	public Integer getEd_coordcliente_id() {
		return ed_coordcliente_id;
	}
	public void setEd_coordcliente_id(Integer ed_coordcliente_id) { //
		this.ed_coordcliente_id = ed_coordcliente_id;
	}
	
	
	public String getE_instalacioncliente() {
		return e_instalacioncliente;
	}
	public void setE_instalacioncliente(String e_instalacioncliente) {
		this.e_instalacioncliente = e_instalacioncliente;
	}
	public String getE_objetivoproyecto() {
		return e_objetivoproyecto;
	}
	public void setE_objetivoproyecto(String e_objetivoproyecto) {
		this.e_objetivoproyecto = e_objetivoproyecto;
	}
	public String getE_nombreproyecto() {
		return e_nombreproyecto;
	}
	public void setE_nombreproyecto(String e_nombreproyecto) {
		this.e_nombreproyecto = e_nombreproyecto;
	}
	public String getE_codigodocumento() {
		return e_codigodocumento;
	}
	public void setE_codigodocumento(String e_codigodocumento) {
		this.e_codigodocumento = e_codigodocumento;
	}
	public Date getE_fechataller() {
		return e_fechataller;
	}
	public void setE_fechataller(Date e_fechataller) {
		this.e_fechataller = e_fechataller;
	}
	public Integer getE_lidertaller() {
		return e_lidertaller;
	}
	public void setE_lidertaller(Integer e_lidertaller) {
		this.e_lidertaller = e_lidertaller;
	}
	public Integer getE_liderproyecto() {
		return e_liderproyecto;
	}
	public void setE_liderproyecto(Integer e_liderproyecto) {
		this.e_liderproyecto = e_liderproyecto;
	}
	public Date getAud_fechacreacion() {
		return aud_fechacreacion;
	}
	public void setAud_fechacreacion(Date aud_fechacreacion) {
		this.aud_fechacreacion = aud_fechacreacion;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getEr_tipoestudio_id() {
		return er_tipoestudio_id;
	}
	public void setEr_tipoestudio_id(Integer er_tipoestudio_id) {
		this.er_tipoestudio_id = er_tipoestudio_id;
	}
	public Integer getEr_proceso_id() {
		return er_proceso_id;
	}
	public void setEr_proceso_id(Integer er_proceso_id) {
		this.er_proceso_id = er_proceso_id;
	}
	public Integer getEd_deptocliente_id2() {
		return ed_deptocliente_id2;
	}
	public void setEd_deptocliente_id2(Integer ed_deptocliente_id2) {
		this.ed_deptocliente_id2 = ed_deptocliente_id2;
	}
	public Integer getEd_deptocliente_id3() {
		return ed_deptocliente_id3;
	}
	public void setEd_deptocliente_id3(Integer ed_deptocliente_id3) {
		this.ed_deptocliente_id3 = ed_deptocliente_id3;
	}
	public Date getE_fechainicio() {
		return e_fechainicio;
	}
	public void setE_fechainicio(Date e_fechainicio) {
		this.e_fechainicio = e_fechainicio;
	}
	public Date getE_fechafin() {
		return e_fechafin;
	}
	public void setE_fechafin(Date e_fechafin) {
		this.e_fechafin = e_fechafin;
	}
	public Integer getE_contacto() {
		return e_contacto;
	}
	public void setE_contacto(Integer e_contacto) {
		this.e_contacto = e_contacto;
	}
	public Date getE_fechacongelamiento() {
		return e_fechacongelamiento;
	}
	public void setE_fechacongelamiento(Date e_fechacongelamiento) {
		this.e_fechacongelamiento = e_fechacongelamiento;
	}
	public Date getE_fechadescongelamiento() {
		return e_fechadescongelamiento;
	}
	public void setE_fechadescongelamiento(Date e_fechadescongelamiento) {
		this.e_fechadescongelamiento = e_fechadescongelamiento;
	}
	
	
}
;
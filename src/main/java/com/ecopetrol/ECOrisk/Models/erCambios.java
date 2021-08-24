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
public class erCambios {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_cambio_id;
	
	@ManyToOne
	@JoinColumn(name="er_olduser",insertable = false, updatable = false)
	private Users usuario;
	private Integer er_olduser;
	
	@ManyToOne
	@JoinColumn(name="er_newuser",insertable = false, updatable = false)
	private Users usuario2;
	private Integer er_newuser;
	
	private Date er_olddate;
	private Date er_newdate;
	
	private Date er_congelamiento;
	private Date er_descongelamiento;
	
	public int getEr_cambio_id() {
		return er_cambio_id;
	}
	public void setEr_cambio_id(int er_cambios_id) {
		this.er_cambio_id = er_cambios_id;
	}
	public Integer getEr_olduser() {
		return er_olduser;
	}
	public void setEr_olduser(Integer er_olduser) {
		this.er_olduser = er_olduser;
	}
	public Integer getEr_newuser() {
		return er_newuser;
	}
	public void setEr_newuser(Integer er_newuser) {
		this.er_newuser = er_newuser;
	}
	public Date getEr_olddate() {
		return er_olddate;
	}
	public void setEr_olddate(Date er_olddate) {
		this.er_olddate = er_olddate;
	}
	public Date getEr_newdate() {
		return er_newdate;
	}
	public void setEr_newdate(Date er_newdate) {
		this.er_newdate = er_newdate;
	}
	public Date getEr_congelamiento() {
		return er_congelamiento;
	}
	public void setEr_congelamiento(Date er_congelamiento) {
		this.er_congelamiento = er_congelamiento;
	}
	public Date getEr_descongelamiento() {
		return er_descongelamiento;
	}
	public void setEr_descongelamiento(Date er_descongelamiento) {
		this.er_descongelamiento = er_descongelamiento;
	}

}



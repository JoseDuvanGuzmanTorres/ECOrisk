package com.ecopetrol.ECOrisk.Models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(exclude="hojas")
@NoArgsConstructor
@AllArgsConstructor
public class er_HojaSeguimiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int htseg_id;

	private Date htseg_fechaescrita;
	
	@Column(length = 2000)
	private String htseg_comentario;
	
	@Column(length = 2000)
	private String htseg_ruta;
	
	@Column(length = 2000)
	private String htseg_evidencia1;
	
	@Column(length = 2000)
	private String htseg_evidencia2;
	
	@Column(length = 2000)
	private String htseg_evidencia3;
	
	@ManyToOne
	@JoinColumn(name="user_id",insertable = false, updatable = false)
	private Users usuario;
	private Integer user_id;
	
	@ManyToOne
	@JoinColumn(name="er_cambio_id",insertable = false, updatable = false)
	private erCambios cambio;
	private Integer er_cambio_id;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "seguimientos")
	private Set<er_HojaTrabajo> hojas = new HashSet<er_HojaTrabajo>();
	
	public int getHtseg_id() {
		return htseg_id;
	}
	public void setHtseg_id(int htseg_id) {
		this.htseg_id = htseg_id;
	}

	public Date getHtseg_fechaescrita() {
		return htseg_fechaescrita;
	}
	public void setHtseg_fechaescrita(Date htseg_fechaescrita) {
		this.htseg_fechaescrita = htseg_fechaescrita;
	}
	
	public String getHtseg_comentario() {
		return htseg_comentario;
	}
	public void setHtseg_comentario(String htseg_comentario) {
		this.htseg_comentario = htseg_comentario;
	}
	public String getHtseg_evidencia1() {
		return htseg_evidencia1;
	}
	public void setHtseg_evidencia1(String htseg_evidencia1) {
		this.htseg_evidencia1 = htseg_evidencia1;
	}
	public String getHtseg_evidencia2() {
		return htseg_evidencia2;
	}
	public void setHtseg_evidencia2(String htseg_evidencia2) {
		this.htseg_evidencia2 = htseg_evidencia2;
	}
	
	public String getHtseg_evidencia3() {
		return htseg_evidencia3;
	}
	public void setHtseg_evidencia3(String htseg_evidencia3) {
		this.htseg_evidencia3 = htseg_evidencia3;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getHtseg_ruta() {
		return htseg_ruta;
	}
	public void setHtseg_ruta(String htseg_ruta) {
		this.htseg_ruta = htseg_ruta;
	}
	public Integer getEr_cambio_id() {
		return er_cambio_id;
	}
	public void setEr_cambio_id(Integer er_cambio_id) {
		this.er_cambio_id = er_cambio_id;
	}
	public Set<er_HojaTrabajo> getHojas() {
		return hojas;
	}
	public void setHojas(Set<er_HojaTrabajo> hojas) {
		this.hojas = hojas;
	}
	
	public void addHojaTrabajo(er_HojaTrabajo hoja) {
		this.hojas.add(hoja);
		hoja.getSeguimientos().add(this);
	}
	
	public void addAllHojaTrabajo(List<er_HojaTrabajo> hoja) {
		this.hojas.addAll(hoja);
		for(er_HojaTrabajo hojita : hoja) {
			hojita.getSeguimientos().add(this);
		}
		
	}
	
	public void removeHojaTrabajo(er_HojaTrabajo hoja) {
		this.hojas.remove(hoja);
		hoja.getSeguimientos().remove(this);
	}
	
}

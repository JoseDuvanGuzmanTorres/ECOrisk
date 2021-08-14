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
public class erRegionales {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_regionalgsc_id;
	
	private String er_regionalgsc;

	public int getEr_regionalgsc_id() {
		return er_regionalgsc_id;
	}

	public void setEr_regionalgsc_id(int er_regionalgsc_id) {
		this.er_regionalgsc_id = er_regionalgsc_id;
	}

	public String getEr_regionalgsc() {
		return er_regionalgsc;
	}

	public void setEr_regionalgsc(String er_regionalgsc) {
		this.er_regionalgsc = er_regionalgsc;
	}

}

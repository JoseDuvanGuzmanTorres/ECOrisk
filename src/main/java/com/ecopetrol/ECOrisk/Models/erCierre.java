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
public class erCierre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_cierre_id;
	
	private String er_cierre;

	public int getEr_cierre_id() {
		return er_cierre_id;
	}

	public void setEr_cierre_id(int er_cierre_id) {
		this.er_cierre_id = er_cierre_id;
	}

	public String getEr_cierre() {
		return er_cierre;
	}

	public void setEr_cierre(String er_cierre) {
		this.er_cierre = er_cierre;
	}

	
}

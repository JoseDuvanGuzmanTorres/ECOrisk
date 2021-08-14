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
public class erSubtema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_subtema_id;
	
	private String er_subtema;

	public int getEr_subtema_id() {
		return er_subtema_id;
	}

	public void setEr_subtema_id(int er_subtema_id) {
		this.er_subtema_id = er_subtema_id;
	}

	public String getEr_subtema() {
		return er_subtema;
	}

	public void setEr_subtema(String er_subtema) {
		this.er_subtema = er_subtema;
	}


	

}

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
public class erTema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int er_tema_id;
	
	private String er_tema;

	public int getEr_tema_id() {
		return er_tema_id;
	}

	public void setEr_tema_id(int er_tema_id) {
		this.er_tema_id = er_tema_id;
	}

	public String getEr_tema() {
		return er_tema;
	}

	public void setEr_tema(String er_tema) {
		this.er_tema = er_tema;
	}

	

}

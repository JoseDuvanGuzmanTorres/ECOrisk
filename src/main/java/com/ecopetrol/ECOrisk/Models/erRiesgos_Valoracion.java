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
public class erRiesgos_Valoracion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rv_id;
	
	private String rv;

	public int getRv_id() {
		return rv_id;
	}

	public void setRv_id(int rv_id) {
		this.rv_id = rv_id;
	}

	public String getRv() {
		return rv;
	}

	public void setRv(String rv) {
		this.rv = rv;
	}
	
	

	
}

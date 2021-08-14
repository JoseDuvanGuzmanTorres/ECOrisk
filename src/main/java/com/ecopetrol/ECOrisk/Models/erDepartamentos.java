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
public class erDepartamentos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ed_deptocliente_id;
	
	private String ed_deptocliente;

	public int getEd_deptocliente_id() {
		return ed_deptocliente_id;
	}

	public void setEd_deptocliente_id(int ed_deptocliente_id) {
		this.ed_deptocliente_id = ed_deptocliente_id;
	}

	public String getEd_deptocliente() {
		return ed_deptocliente;
	}

	public void setEd_deptocliente(String ed_deptocliente) {
		this.ed_deptocliente = ed_deptocliente;
	}

	
}

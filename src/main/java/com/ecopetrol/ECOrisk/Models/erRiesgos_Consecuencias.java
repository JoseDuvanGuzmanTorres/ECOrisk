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
public class erRiesgos_Consecuencias {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rc_id;
	
	private String rc;

	public int getRc_id() {
		return rc_id;
	}

	public void setRc_id(int rc_id) {
		this.rc_id = rc_id;
	}

	public String getRc() {
		return rc;
	}

	public void setRc(String rc) {
		this.rc = rc;
	}

	

}

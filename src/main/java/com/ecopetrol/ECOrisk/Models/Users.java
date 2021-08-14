package com.ecopetrol.ECOrisk.Models;


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
public class Users {
		
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
	private int id;
    private String fullname;
	private String username;
	private String password;
	
	@ManyToOne
	@JoinColumn(name="roles_id",insertable = false, updatable = false)
	private Roles roles;
	private Integer roles_id;
	
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getRoles_id() {
		return roles_id;
	}
	public void setRoles_id(Integer roles_id) {
		this.roles_id = roles_id;
	}
	
}

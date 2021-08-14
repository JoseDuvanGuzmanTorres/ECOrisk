package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.Roles;
import com.ecopetrol.ECOrisk.Repositories.RolesRepository;

@Service
public class RolesService {
	
	@Autowired
	RolesRepository rolesRepository;
	
	
	public List<Roles> getRoles(){
		return rolesRepository.findAll();
	}

	//Update
	public void save(Roles rol) {
		rolesRepository.save(rol);
	}
	
	public Optional<Roles> getById(int id) {
		return rolesRepository.findById(id);
	}
	
}

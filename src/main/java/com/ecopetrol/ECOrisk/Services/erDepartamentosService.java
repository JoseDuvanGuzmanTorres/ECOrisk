package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erDepartamentos;
import com.ecopetrol.ECOrisk.Repositories.erDepartamentosRepository;

@Service
public class erDepartamentosService {

	@Autowired
	private erDepartamentosRepository ErDepartamentosRepository;
	
	public List<erDepartamentos> getDepartamentos(){
		return ErDepartamentosRepository.findAll();
	}
	
	//Update
	public void save(erDepartamentos departamento) {
		ErDepartamentosRepository.save(departamento);
	}
	
	public Optional<erDepartamentos> getById(int id) {
		return ErDepartamentosRepository.findById(id);
	}
	
	public erDepartamentos getByDepartamento(String departamento){
		return ErDepartamentosRepository.findByDepartamento(departamento);
		
	}
	
	//Custom Filter
	public List<erDepartamentos> getDepartamentosAsc(){
		return ErDepartamentosRepository.findByAllAsc();
	}
	
	
	
}

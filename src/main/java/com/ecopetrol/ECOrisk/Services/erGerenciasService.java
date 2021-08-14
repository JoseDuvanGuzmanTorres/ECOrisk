package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erGerencias;
import com.ecopetrol.ECOrisk.Repositories.erGerenciasRepository;

@Service
public class erGerenciasService {

	@Autowired
	private erGerenciasRepository ErGerenciasRepository;
	
	public List<erGerencias> getGerencias(){
		return ErGerenciasRepository.findAll();
	}
	
	//Update Country
	public void save( erGerencias gerencia) {
		ErGerenciasRepository.save(gerencia);
	}
	
	public Optional<erGerencias> getById(int id) {
		return ErGerenciasRepository.findById(id);
	}
	
	public erGerencias getByGerencia(String gerencia){
		return ErGerenciasRepository.findByGerencia(gerencia);
		
	}
	
	//Custom Filter
	public List<erGerencias> getGerenciasAsc(){
		return ErGerenciasRepository.findByAllAsc();
	}
}

package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erEtapas;
import com.ecopetrol.ECOrisk.Repositories.erEtapasRepository;

@Service
public class erEtapasService {
	
	@Autowired
	erEtapasRepository ErEtapasRepository;
	
	
	public List<erEtapas> getEtapas(){
		return ErEtapasRepository.findAll();
	}

	//Update
	public void save(erEtapas departamento) {
		ErEtapasRepository.save(departamento);
	}
	
	public Optional<erEtapas> getById(int id) {
		return ErEtapasRepository.findById(id);
	}
	
	erEtapas getByEtapa(String etapa){
		return ErEtapasRepository.findByEtapa(etapa);
		
	}
	
	//Custom Filter
	public List<erEtapas> getEtapasAsc(){
		return ErEtapasRepository.findByAllAsc();
	}
}

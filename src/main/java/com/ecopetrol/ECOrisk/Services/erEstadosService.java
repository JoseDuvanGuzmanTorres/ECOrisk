package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erEstados;
import com.ecopetrol.ECOrisk.Repositories.erEstadosRepository;

@Service
public class erEstadosService {

	@Autowired
	private erEstadosRepository ErEstadosRepository;
	
	public List<erEstados> getEstados(){
		return ErEstadosRepository.findAll();
	}
	
	//Update
	public void save(erEstados estados) {
		ErEstadosRepository.save(estados);
	}
	
	public Optional<erEstados> getById(int id) {
		return ErEstadosRepository.findById(id);
	}
	
	public erEstados getByEstado(String estado){
		return ErEstadosRepository.findByEstado(estado);
		
	}
	
	//Custom Filter
	public List<erEstados> getEstadosAsc(){
		return ErEstadosRepository.findByAllAsc();
	}
	
	
	
}

package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erEstadopro;
import com.ecopetrol.ECOrisk.Repositories.erEstadoproyectoRepository;

	@Service
public class erEstadoproyectoService {
	
		@Autowired
	private erEstadoproyectoRepository erEstadoproyectoRepository;
	
	
	public List<erEstadopro> getEstadoproyecto(){
		return erEstadoproyectoRepository.findAll();
	}
	
	//Update
	public void save(erEstadopro proceso) {
		erEstadoproyectoRepository.save(proceso);
	}
	
	public Optional<erEstadopro> getById(int id) {
		return erEstadoproyectoRepository.findById(id);
	}
	
	public erEstadopro getByEstadopro(String estado){
		return erEstadoproyectoRepository.findByEstadopro(estado);
		
	}
	
	//Custom Filter
	public List<erEstadopro> getProcesosAsc(){
		return erEstadoproyectoRepository.findByAllAsc();
	}
	
}

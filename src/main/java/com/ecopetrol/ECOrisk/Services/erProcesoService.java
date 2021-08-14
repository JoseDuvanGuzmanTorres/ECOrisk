package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erProceso;
import com.ecopetrol.ECOrisk.Repositories.erProcesoRepository;

@Service
public class erProcesoService {

	@Autowired
	private erProcesoRepository ErProcesoRepository;
	
	public List<erProceso> getProcesos(){
		return ErProcesoRepository.findAll();
	}
	
	//Update
	public void save(erProceso proceso) {
		ErProcesoRepository.save(proceso);
	}
	
	public Optional<erProceso> getById(int id) {
		return ErProcesoRepository.findById(id);
	}
	
	public erProceso getByProceso(String proceso){
		return ErProcesoRepository.findByProceso(proceso);
		
	}
	
	//Custom Filter
	public List<erProceso> getProcesosAsc(){
		return ErProcesoRepository.findByAllAsc();
	}
	
	
	
}

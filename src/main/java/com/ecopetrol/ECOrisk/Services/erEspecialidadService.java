package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecopetrol.ECOrisk.Models.erEspecialidad;
import com.ecopetrol.ECOrisk.Repositories.erEspecialidadRepository;

@Service
public class erEspecialidadService {

	@Autowired
	private erEspecialidadRepository ErEspecialidadRepository;
	
	public List<erEspecialidad> getEspecialidad(){
		return ErEspecialidadRepository.findAll();
	}
	
	//Update
	public void save(erEspecialidad especialidad) {
		ErEspecialidadRepository.save(especialidad);
	}
	
	public Optional<erEspecialidad> getById(int id) {
		return ErEspecialidadRepository.findById(id);
	}
	
	public erEspecialidad getByDepartamento(String especialidad){
		return ErEspecialidadRepository.findByEspecialidad(especialidad);
		
	}
	
	//Custom Filter
	public List<erEspecialidad> getEspecialidadAsc(){
		return ErEspecialidadRepository.findByAllAsc();
	}
	
	
	
	
}

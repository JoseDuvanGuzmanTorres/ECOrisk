package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erRiesgos_Probabilidad;
import com.ecopetrol.ECOrisk.Repositories.erRiesgos_ProbabilidadRepository;

@Service
public class erRiesgos_ProbabilidadService {

	@Autowired
	private erRiesgos_ProbabilidadRepository ErRiesgos_ProbabilidadRepository;
	
	public List<erRiesgos_Probabilidad> getValoraciones(){
		return ErRiesgos_ProbabilidadRepository.findAll();
	}
	
	//Update
	public void save(erRiesgos_Probabilidad valoracion) {
		ErRiesgos_ProbabilidadRepository.save(valoracion);
	}
	
	public Optional<erRiesgos_Probabilidad> getById(int id) {
		return ErRiesgos_ProbabilidadRepository.findById(id);
	}
	
	public erRiesgos_Probabilidad getByValoracion(String valoracion){
		return ErRiesgos_ProbabilidadRepository.findByValoracion(valoracion);
		
	}
	
	//Custom Filter
	public List<erRiesgos_Probabilidad> getValoracionesAsc(){
		return ErRiesgos_ProbabilidadRepository.findByAllAsc();
	}
	
	
}

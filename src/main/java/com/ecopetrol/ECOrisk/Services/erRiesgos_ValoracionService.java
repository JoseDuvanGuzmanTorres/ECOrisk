package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erRiesgos_Valoracion;
import com.ecopetrol.ECOrisk.Projections.erRiesgos_ValoracionProjection;
import com.ecopetrol.ECOrisk.Repositories.erRiesgos_ValoracionRepository;

@Service
public class erRiesgos_ValoracionService {

	@Autowired
	private erRiesgos_ValoracionRepository ErRiesgos_ValoracionesRepository;
	
	public List<erRiesgos_Valoracion> getValoraciones(){
		return ErRiesgos_ValoracionesRepository.findAll();
	}
	
	//Update
	public void save(erRiesgos_Valoracion valoracion) {
		ErRiesgos_ValoracionesRepository.save(valoracion);
	}
	
	public Optional<erRiesgos_Valoracion> getById(int id) {
		return ErRiesgos_ValoracionesRepository.findById(id);
	}
	
	public erRiesgos_Valoracion getByValoracion(String valoracion){
		return ErRiesgos_ValoracionesRepository.findByValoracion(valoracion);
		
	}
	
	//Custom Filter
	public List<erRiesgos_Valoracion> getValoracionesAsc(){
		return ErRiesgos_ValoracionesRepository.findByAllAsc();
	}
	
	//Projection
	public erRiesgos_ValoracionProjection getValoracionesProjection(){
		return ErRiesgos_ValoracionesRepository.findAllProjection();
	}
	
	
}

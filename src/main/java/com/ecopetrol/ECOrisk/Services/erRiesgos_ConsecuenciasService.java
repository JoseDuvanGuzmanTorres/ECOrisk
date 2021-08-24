package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erRiesgos_Consecuencias;
import com.ecopetrol.ECOrisk.Repositories.erRiesgos_ConsecuenciasRepository;

@Service
public class erRiesgos_ConsecuenciasService {

	@Autowired
	private erRiesgos_ConsecuenciasRepository ErRiesgos_ConsecuenciasRepository;
	
	public List<erRiesgos_Consecuencias> getValoraciones(){
		return ErRiesgos_ConsecuenciasRepository.findAll();
	}
	
	//Update
	public void save(erRiesgos_Consecuencias valoracion) {
		ErRiesgos_ConsecuenciasRepository.save(valoracion);
	}
	
	public Optional<erRiesgos_Consecuencias> getById(int id) {
		return ErRiesgos_ConsecuenciasRepository.findById(id);
	}
	
	public erRiesgos_Consecuencias getByValoracion(String valoracion){
		return ErRiesgos_ConsecuenciasRepository.findByValoracion(valoracion);
		
	}
	
	//Custom Filter
		public List<erRiesgos_Consecuencias> getValoracionesAsc(){
			return ErRiesgos_ConsecuenciasRepository.findByAllAsc();
		}
	
	
	
}

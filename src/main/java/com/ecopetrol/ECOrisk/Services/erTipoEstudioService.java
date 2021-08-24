package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erTipoEstudio;
import com.ecopetrol.ECOrisk.Repositories.erTipoEstudioRepository;

@Service
public class erTipoEstudioService {

	@Autowired
	private erTipoEstudioRepository erTipoEstudioRepository;
	
	public List<erTipoEstudio> getTiposDeEstudio(){
		return erTipoEstudioRepository.findAll();
	}
	
	//Update
	public void save(erTipoEstudio tipoEstudio) {
		erTipoEstudioRepository.save(tipoEstudio);
	}
	
	public Optional<erTipoEstudio> getById(int id) {
		return erTipoEstudioRepository.findById(id);
	}
	
	//Custom Filter
	public erTipoEstudio getByTipoEstudio(String tipoEstudio){
		return erTipoEstudioRepository.findByTipoEstudio(tipoEstudio);
		
	}
	
	//Custom Filter
	public List<erTipoEstudio> getTiposDeEstudioAsc(){
		return erTipoEstudioRepository.findByAllAsc();
	}
	
	
	
}

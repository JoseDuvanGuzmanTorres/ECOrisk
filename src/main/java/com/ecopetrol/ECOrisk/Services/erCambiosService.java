package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erCambios;
import com.ecopetrol.ECOrisk.Projections.erCambiosProjection;
import com.ecopetrol.ECOrisk.Repositories.erCambiosRepository;

@Service
public class erCambiosService {
	
	@Autowired
	erCambiosRepository ErCambiosRepository;
	
	
	public List<erCambios> getRoles(){
		return ErCambiosRepository.findAll();
	}

	//Update
	public void save(erCambios cambio) {
		ErCambiosRepository.save(cambio);
	}
	
	public Optional<erCambios> getById(int id) {
		return ErCambiosRepository.findById(id);
	}
	
	//Projection
	public List<erCambiosProjection> getAllProjectionByIdEncabeAndUserId(Integer hojatrabajo_id) {
		return ErCambiosRepository.findByHojaTrabajoIdProjection(hojatrabajo_id);
	}
	
}

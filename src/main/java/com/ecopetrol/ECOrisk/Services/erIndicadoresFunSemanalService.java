package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erIndicadoresFunSemanal;
import com.ecopetrol.ECOrisk.Projections.erIndicadoresFunSemanalProjection;
import com.ecopetrol.ECOrisk.Repositories.erIndicadoresFunSemanalRepository;

@Service
public class erIndicadoresFunSemanalService {

	@Autowired
	private erIndicadoresFunSemanalRepository ErIndicadoresFunSemanalRepository;
	
	public List<erIndicadoresFunSemanal> getIndicaFunSem(){
		return ErIndicadoresFunSemanalRepository.findAll();
	}
	
	//Update
	public void save(erIndicadoresFunSemanal indicador) {
		ErIndicadoresFunSemanalRepository.save(indicador);
	}
	
	public void saveAll(List<erIndicadoresFunSemanal> indicadores) {
		ErIndicadoresFunSemanalRepository.saveAll(indicadores);
	}
	
	public Optional<erIndicadoresFunSemanal> getById(int id) {
		return ErIndicadoresFunSemanalRepository.findById(id);
	}
	
	
	//Projection
	public List<erIndicadoresFunSemanalProjection> getAllIndicadoresFuncionarioSem(Integer dueno ){
		return ErIndicadoresFunSemanalRepository.findAllProjectionByFuncionario(dueno);
		
	}
	
	
}

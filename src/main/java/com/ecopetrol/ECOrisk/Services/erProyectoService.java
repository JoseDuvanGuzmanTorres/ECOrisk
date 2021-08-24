package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erProyecto;
import com.ecopetrol.ECOrisk.Projections.IndicadoresProjection;
import com.ecopetrol.ECOrisk.Projections.erProyectoProjection;
import com.ecopetrol.ECOrisk.Repositories.erProyectoRepository;

@Service
public class erProyectoService {

	@Autowired
	private erProyectoRepository ErProyectoRepository;
	
	public List<erProyecto> getProyectos(){
		return ErProyectoRepository.findAll();
	}
	
	//Update
	public void save(erProyecto proyecto) {
		ErProyectoRepository.save(proyecto);
	}
	
	public Optional<erProyecto> getById(int id) {
		return ErProyectoRepository.findById(id);
	}
	
	public erProyecto getByProyectoCod(String codigo_proyecto){
		return ErProyectoRepository.findByProyectoCod(codigo_proyecto);
		
	}
	public List<erProyecto> getByEncabezados(){
		return ErProyectoRepository.findByEncabezadoProId();
		
	}
	
	public List<erProyecto> getByDueno(String dueno){
		return ErProyectoRepository.findByDueno(dueno);
		
	}
	
	//Projection
	public List<IndicadoresProjection> getAllIndicadoresProyecto(Integer dueno ){
		return ErProyectoRepository.findAllIndicadoresProyecto(dueno);
		
	}
	
	public List<IndicadoresProjection> getAllIndicadoresLA(Integer dueno ){
		return ErProyectoRepository.findAllIndicadoresLA(dueno);
		
	}
	public List<IndicadoresProjection> getAllIndicadoresFuncionarioProceso(Integer dueno, Integer proceso ){
		return ErProyectoRepository.findAllIndicadoresFuncionarioProceso(dueno, proceso);
		
	}
	public List<IndicadoresProjection> getAllIndicadoresFuncionario(Integer dueno ){
		return ErProyectoRepository.findAllIndicadoresFuncionario(dueno);
		
	}
	
	public List<IndicadoresProjection> getAllIndicadoresProyectoByLider(Integer lider ){
		return ErProyectoRepository.findAllIndicadoresProyectoByLider(lider);
		
	}
	
	public List<erProyectoProjection> getAllProyectoProjection(){
		return ErProyectoRepository.findAllProyectosProjection();
		
	}
	
	
	
	
	
}

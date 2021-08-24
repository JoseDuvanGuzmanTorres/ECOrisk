package com.ecopetrol.ECOrisk.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erEncabezado;
import com.ecopetrol.ECOrisk.Projections.ContadorTalleresProjection;
import com.ecopetrol.ECOrisk.Projections.erEncabezadoProjection;
import com.ecopetrol.ECOrisk.Repositories.erEncabezadoRepository;

@Service
public class erEncabezadoService {

	@Autowired
	private erEncabezadoRepository ErEncabezadoRepository;
	
	public List<erEncabezado> getEncabezados(){
		return ErEncabezadoRepository.findAll();
	}
	
	//Update Country
	public void save(erEncabezado encabezado) {
		ErEncabezadoRepository.save(encabezado);
	}
	
	public Optional<erEncabezado> getById(int id) {
		return ErEncabezadoRepository.findById(id);
	}
	//Custom filter
	public erEncabezado getByCodDoc(String regional){
		return ErEncabezadoRepository.findByCodDoc(regional);
	}
	
	public List<erEncabezado> getEncabezadosByResponsable(Integer user_id){
		return ErEncabezadoRepository.findAllByResponsable(user_id);
	}
	
	//Custom filter
	public erEncabezado getByWie_Id(Integer Wie_id){
		return ErEncabezadoRepository.findByEncabezado_Id(Wie_id);
	}
	
	public List<erEncabezado> getByProId(Integer ProId){
		return ErEncabezadoRepository.findByProId(ProId);
	}
	
	public List<String> getByProIdYDueno(Integer ProId){
		return ErEncabezadoRepository.findByProIdYDueno(ProId);
	}
	
	public List<String> getByAllDueno(){
		return ErEncabezadoRepository.findByAllDueno();
	}
	
	public List<erEncabezado> getEncabezadosByDueno(Integer DuenoId){
		return ErEncabezadoRepository.findEncabeByDueno(DuenoId);
	}
	
	public List<erEncabezado> getEncabezadosByList(List<Integer> id_encabeList){
		return ErEncabezadoRepository.findEncabeByList(id_encabeList);
	}
	

	//Projection


	public List<erEncabezadoProjection> getProjectionByUser(String tipoestudio,String proceso,String proyecto,String codigo, String nombre,String objetivo,String etapa,String regional, String gerencia, String dueno, String departamento, String lider, String instalacion, Date fechainicial, Date fechafinal, Integer user_id){
		
		return ErEncabezadoRepository.findByMultipleFiltersAndUserProjection(tipoestudio, proceso, proyecto, codigo, nombre, objetivo, etapa , regional, gerencia, dueno, departamento, lider, instalacion,fechainicial, fechafinal, user_id);
	}
	
	public List<erEncabezadoProjection> getProjectionByUserResponsanleT(String tipoestudio,String proceso,String proyecto,String codigo, String nombre,String objetivo,String etapa,String regional, String gerencia, String dueno,String responsable, String departamento, String lider, String instalacion, Date fechainicial, Date fechafinal, Integer user_id){
		
		return ErEncabezadoRepository.findByMultipleFiltersAndUserProjectionResponsableT(tipoestudio, proceso, proyecto, codigo, nombre, objetivo, etapa , regional, gerencia, dueno,responsable, departamento, lider, instalacion,fechainicial, fechafinal, user_id);
	}
	public ContadorTalleresProjection getContadorTalleres() {
		
		return ErEncabezadoRepository.findByContadoresTalleres();
	}
	
}

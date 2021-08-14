package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecopetrol.ECOrisk.Models.er_HojaTrabajo;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoLeccionesAProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoREmergenteProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoRMaterializadoProjection;
import com.ecopetrol.ECOrisk.Repositories.er_HojaTrabajoRepository;

@Service
public class er_HojaTrabajoService {

	@Autowired
	private er_HojaTrabajoRepository Er_HojaTrabajoRepository;
	
	public List<er_HojaTrabajo> getHojaTrabajo(){
		return Er_HojaTrabajoRepository.findAll();
	}
	
	//Update Country
	public void save(er_HojaTrabajo hojatrabajo) {
		Er_HojaTrabajoRepository.save(hojatrabajo);
	}
	
	//Update Country
	public void saveAll(List<er_HojaTrabajo> hojastrabajo) {
		Er_HojaTrabajoRepository.saveAll(hojastrabajo);
	}
		
	
	public Optional<er_HojaTrabajo> getById(int id) {
		return Er_HojaTrabajoRepository.findById(id);
	}
	
	
	
	//Custom Multi Filter

	public List<er_HojaTrabajo> getHojaTrabajoByNoFechaCierre() {
		return Er_HojaTrabajoRepository.findByNoFechaCierre();
	}
	
	public List<er_HojaTrabajo> getHojaTrabajoByFuncionario(Integer funcionario) {
		return Er_HojaTrabajoRepository.findByFuncionario(funcionario);
	}
	
	public List<er_HojaTrabajo> getHojaTrabajoByProyectoYEstado(String codproyecto, Integer estado) {
		return Er_HojaTrabajoRepository.findHojasByProyectoYEstado(codproyecto, estado);
	}
	
	public List<er_HojaTrabajo> getHojaTrabajoByDuenoProyecto(Integer funcionario) {
		return Er_HojaTrabajoRepository.findByDuenoProyecto(funcionario);
	}
	
	public List<er_HojaTrabajo> getHojaTrabajoByEncabeIdListDuenoProyecto(List<Integer> id_encabeList, Integer funcionario) {
		return Er_HojaTrabajoRepository.findByEncabeIdListAndDuenoProyecto(id_encabeList,funcionario);
	}
	
	
	public List<er_HojaTrabajo> getHojaTrabajoByEncabezadoMultiple(Integer encabezado_id ,  String pregunta, String evento , String especialidad, String matriz, String salvaguardas, String raconsecuencias,
			String raprobabilidad, String ravaloracion, String recomendacion){
		return Er_HojaTrabajoRepository.findByEncabezadoMultiple(encabezado_id,pregunta,evento,especialidad,matriz,salvaguardas,raconsecuencias,raprobabilidad,ravaloracion, recomendacion);
	}
	
	public List<er_HojaTrabajo> getHojaTrabajoByEncabezado(Integer encabezado_id){
		return Er_HojaTrabajoRepository.findByEncabezado(encabezado_id);
	}
	
	public er_HojaTrabajo getByHt_id(Integer Ht_id) {
		return Er_HojaTrabajoRepository.findByHt_Id(Ht_id);
	}
	public List<er_HojaTrabajo> getByRiskActual_id(Integer risk) {
		return Er_HojaTrabajoRepository.findByRiskActual(risk);
	}
	public List<er_HojaTrabajo> getByProId(Integer ProId) {
		return Er_HojaTrabajoRepository.findByProId(ProId);
	}
	
	public List<er_HojaTrabajo> getByRiskAndProject(Integer ProId, Integer risk) {
		return Er_HojaTrabajoRepository.findByRiskAndProject(ProId,risk);
	}
	
	public List<er_HojaTrabajo> getByRiskAndFuncionario(String funcionario, Integer risk) {
		return Er_HojaTrabajoRepository.findByRiskAndFuncionario(funcionario,risk);
	}
	
	public Integer CountUniqueEventByProId(Integer ProId) {
		return Er_HojaTrabajoRepository.CountUniqueEventByProId(ProId);
	}
	
	public List<er_HojaTrabajo> getByHojaTrabajoCambios() {
		return Er_HojaTrabajoRepository.findByHojaTrabajoCambios();
	}
	
	public List<er_HojaTrabajo> getHojaByEncabeIdList(List<Integer> id_encabeList) {
		return Er_HojaTrabajoRepository.findHojaByEncabeIdList(id_encabeList);
	}
	
	public List<er_HojaTrabajo> getHojaByEncabeIdListYEstado(List<Integer> id_encabeList, Integer estado) {
		return Er_HojaTrabajoRepository.findHojaByEncabeIdListYEstado(id_encabeList, estado);
	}
	
	public List<er_HojaTrabajo> getHojaByEncabeIdListAndUserId(List<Integer> id_encabeList, Integer userId) {
		return Er_HojaTrabajoRepository.findHojaByEncabeIdListAndUserId(id_encabeList,userId);
	}
	
	//Projection
	public List<er_HojaTrabajoProjection> getAllProjection() {
		return Er_HojaTrabajoRepository.findAllProjection();
	}
	
	//Projection
	public List<er_HojaTrabajoProjection> getAllProjectionByIdEncabeAndUserId(List<Integer> id_encabeList, String tipo, Integer userId) {
		return Er_HojaTrabajoRepository.findAllProjectionByIdEncabeAndUserId(id_encabeList, tipo, userId);
	}
	
	//Projection
	public List<er_HojaTrabajoProjection> getAllProjectionAbiertasByUserId(Integer userId) {
		return Er_HojaTrabajoRepository.findAllProjectionAbiertasByUserId(userId);
	}
	
	//Projection
	public List<er_HojaTrabajoProjection> getAllProjectionAbiertasByDueno(Integer userId) {
		return Er_HojaTrabajoRepository.findAllProjectionAbiertasByDueno(userId);
	}

	//Projection LA
	public List<er_HojaTrabajoLeccionesAProjection> getAllHojaTrabajoLAProjectionByEncabezado(List<Integer> id_encabeList,
			Integer userId) {
		return Er_HojaTrabajoRepository.findHojaTrabajoLAProjectionByEncabezado(id_encabeList,userId);
	}
	//projection LA por user Id
	public List<er_HojaTrabajoLeccionesAProjection> getAllHojaTrabajoLAProjectionByUserId(Integer userId) {
		return Er_HojaTrabajoRepository.findHojaTrabajoLAProjectionByUserId(userId);
	}
	
	//Projection RM
	public List<er_HojaTrabajoRMaterializadoProjection> getAllHojaTrabajoRMProjectionByEncabezado(List<Integer> id_encabeList,
			Integer userId) {
		return Er_HojaTrabajoRepository.findHojaTrabajoRMProjectionByEncabezado(id_encabeList,userId);
	}

	//Projection RE
	public List<er_HojaTrabajoREmergenteProjection> getAllHojaTrabajoREProjectionByEncabezado(List<Integer> id_encabeList,
			Integer userId) {
		return Er_HojaTrabajoRepository.findHojaTrabajoREProjectionByEncabezado(id_encabeList,userId);
	}

}

package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.er_HojaSeguimiento;
import com.ecopetrol.ECOrisk.Projections.er_HojaSeguimientoProjection;
import com.ecopetrol.ECOrisk.Repositories.er_HojaSeguimientoRepository;

@Service
public class er_HojaSeguimientoService {

	@Autowired
	private er_HojaSeguimientoRepository Er_HojaSeguimientoRepository;
	
	public List<er_HojaSeguimiento> getHojaSeguimiento(){
		return Er_HojaSeguimientoRepository.findAll();
	}
	
	//Update Comentarios
	public void save(er_HojaSeguimiento hojacomentario) {
		Er_HojaSeguimientoRepository.save(hojacomentario);
	}
	
	public Optional<er_HojaSeguimiento> getById(int id) {
		return Er_HojaSeguimientoRepository.findById(id);
	}
	
	
	//Custom Filter
	/*public List<er_HojaSeguimiento> getHojaSeguimientoByTrabajoId(Integer hojatrabajo_id){
		return Er_HojaSeguimientoRepository.findByHojaTrabajoId(hojatrabajo_id);
	}
	*/
	
	//Projection
	public List<er_HojaSeguimientoProjection> getSeguimientoProjectionByTrabajoId(Integer hojatrabajo_id){
		return Er_HojaSeguimientoRepository.findByHojaTrabajoIdProjection(hojatrabajo_id);
	}
	public List<er_HojaSeguimientoProjection> getSeguimientoProjectionByEncabeId(Integer encabezado_id){
		return Er_HojaSeguimientoRepository.findByEncabezadoIdProjection(encabezado_id);
	}
}


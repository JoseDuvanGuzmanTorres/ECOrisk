package com.ecopetrol.ECOrisk.Controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Models.erIndicadoresFunSemanal;
import com.ecopetrol.ECOrisk.Projections.IndicadoresProjection;
import com.ecopetrol.ECOrisk.Projections.erIndicadoresFunSemanalProjection;
import com.ecopetrol.ECOrisk.Services.erProyectoService;
import com.ecopetrol.ECOrisk.Services.UserService;
import com.ecopetrol.ECOrisk.Services.erIndicadoresFunSemanalService;

/**
 * 
 * Controlador para la seccion de indicadores
 * @author Manuel Eduardo Patarroyo Santos
 * 
 * 
 */


@Controller
public class IndicadoresController {

	@Autowired
	private erProyectoService ErProyectoService;
	@Autowired
	private UserService userService;
	@Autowired
	private erIndicadoresFunSemanalService ErIndicadoresFunSemanalService;
	
	/* 
	 * se da el mapeo y los datos a consultar en los indicadores por funcionario 
	 * 
	 */
	
	@GetMapping("/indicadores/funcionario")
	public String Funcionario(Model model,Principal principal) {
		Integer Id;
		String username = principal.getName();
		//se llaman los datos por username y rol
		Users users = userService.loadUserByUsername(username);
		if(users.getRoles_id() == 2 || users.getRoles_id() == 3 || users.getRoles_id() == 6 || users.getRoles_id() == 7) {
			Id = null;
		}else {
			Id = users.getId();
		}
		//se hace una lista de indicadores por funcionario OPEX
		List<IndicadoresProjection> IndicadoresFuncionarioProcesoOPList = ErProyectoService.getAllIndicadoresFuncionarioProceso(Id, 1);
		model.addAttribute("IndicadoresFuncionarioProcesoOPList", IndicadoresFuncionarioProcesoOPList);
		//se hace una lista de indicadores por funcionario CAPEX
		List<IndicadoresProjection> IndicadoresFuncionarioProcesoCAList = ErProyectoService.getAllIndicadoresFuncionarioProceso(Id, 2);
		model.addAttribute("IndicadoresFuncionarioProcesoCAList", IndicadoresFuncionarioProcesoCAList);
		model.addAttribute("Funcionario", "Funcionario");

		return "indicadoresfuncionario";
	}
	
	
	@GetMapping("/indicadores/proyectos")
	public String Proyectos(Model model,Principal principal) {
		Integer Id;
		String username = principal.getName();
		Users users = userService.loadUserByUsername(username);
		if(users.getRoles_id() == 2 || users.getRoles_id() == 3 || users.getRoles_id() == 6 || users.getRoles_id() == 7) {
			Id = null;
		}else {
			Id = users.getId();
		}
		
		
		List<IndicadoresProjection> IndicadoresLA = ErProyectoService.getAllIndicadoresLA(Id);
		model.addAttribute("IndicadoresLA", IndicadoresLA);
		model.addAttribute("Funcionario", "Proyectos");
		
		List<IndicadoresProjection> IndicadoresProyectoList = ErProyectoService.getAllIndicadoresProyecto(Id);
		model.addAttribute("IndicadoresProyectoList", IndicadoresProyectoList);
		model.addAttribute("Funcionario", "Proyectos");
		
		

		return "indicadores";
	}
	
		
	@Scheduled(cron = "0 0 23 * * 5")
	void IndicadoresFuncionarioSemanales() {
		 Integer Id = null;		 
		 List<IndicadoresProjection> IndicadoresFuncionarioList = ErProyectoService.getAllIndicadoresFuncionario(Id);
		 List<erIndicadoresFunSemanal> Semanal = new ArrayList<erIndicadoresFunSemanal>();
		 Date fecha = new Date();
		 for(IndicadoresProjection indicador : IndicadoresFuncionarioList) {
			 erIndicadoresFunSemanal nuevo = new erIndicadoresFunSemanal();
			 
			 nuevo.setFecha(fecha);
			 nuevo.setIddue(indicador.getIddue());
			 nuevo.setEventos(indicador.getEventos());
			 nuevo.setRiesgosvh(indicador.getRiesgosvh());
			 nuevo.setRiesgosh(indicador.getRiesgosh());
			 nuevo.setRiesgosm(indicador.getRiesgosm());
			 nuevo.setRiesgosl(indicador.getRiesgosl());
			 nuevo.setRiesgostotal(indicador.getRiesgostotal());
			 nuevo.setAbiertostotal(indicador.getAbiertostotal());
			 nuevo.setAbiertosvencidos(indicador.getAbiertosvencidos());
			 nuevo.setCerradostotal(indicador.getCerradostotal());
			 nuevo.setPromgaps(indicador.getPromgaps());
			 nuevo.setCumplimiento(indicador.getCumplimiento());
			 nuevo.setWhatif(indicador.getWhatif());
			 nuevo.setConstructabilidad(indicador.getConstructabilidad());
			 nuevo.setDreview(indicador.getDreview());
			 nuevo.setPreview(indicador.getPreview());
		
			 Semanal.add(nuevo);
		 }
		 
		 ErIndicadoresFunSemanalService.saveAll(Semanal);
		 
	 }
	
	@GetMapping("/indicadores/semanal")
	public String FuncionarioSemanal(Model model,Principal principal) {
		Integer Id;
		String username = principal.getName();
		Users users = userService.loadUserByUsername(username);
		if(users.getRoles_id() == 2 || users.getRoles_id() == 3 || users.getRoles_id() == 6 || users.getRoles_id() == 7) {
			Id = null;
		}else {
			Id = users.getId();
		}
		
		List<erIndicadoresFunSemanalProjection> IndicadoresProyectoList = ErIndicadoresFunSemanalService.getAllIndicadoresFuncionarioSem(Id);
		model.addAttribute("IndicadoresProyectoList", IndicadoresProyectoList);
		model.addAttribute("Funcionario", "Funcionario Semanal");

		return "indicadoressemanal";
	}

}

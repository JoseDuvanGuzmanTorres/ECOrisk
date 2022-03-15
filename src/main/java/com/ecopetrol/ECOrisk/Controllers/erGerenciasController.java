package com.ecopetrol.ECOrisk.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecopetrol.ECOrisk.Models.erGerencias;
import com.ecopetrol.ECOrisk.Services.erGerenciasService;


/**
 * 
 * 
 * @author Manuel Eduardo Patarroyo Santos
 * 
 * 
 */

@Controller
public class erGerenciasController {

	@Autowired
	private erGerenciasService ErGerenciasService;
	
	@GetMapping("/config/gerencias")
	public String getEtapas(Model model) {
		
		List<erGerencias> gerenciasList = ErGerenciasService.getGerencias();
		model.addAttribute("gerenciasList",gerenciasList);
		return "gerencias";
	}
}

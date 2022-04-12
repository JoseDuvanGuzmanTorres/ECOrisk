package com.ecopetrol.ECOrisk.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecopetrol.ECOrisk.Models.erEtapas;
import com.ecopetrol.ECOrisk.Services.erEtapasService;


/**
 * 
 * Se inizializan las listas de etapas y se obtienen sus valores para ser usados
 * @author Manuel Eduardo Patarroyo Santos
 * 
 * 
 */


@Controller
public class erEtapasController {

	@Autowired
	private erEtapasService ErEtapasService;
	
	@GetMapping("/config/etapas") //Se declara el mapeo de direccion de las etapas 
	/*
	 * 
	 * Rutina para crear el modelo de las etapas, se crea una lista de etapas 
	 * 
	 */	
	public String getEtapas(Model model) {
		
		List<erEtapas> etapasList = ErEtapasService.getEtapas();
		model.addAttribute("etapasList",etapasList);
		return "etapas";
	}
	//se retorna una etapa nueva cuando aplique
	erEtapas etapa1 = new erEtapas();
	
}

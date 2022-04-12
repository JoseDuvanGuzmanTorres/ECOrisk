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
 * Controlador de gerencias
 * @author Manuel Eduardo Patarroyo Santos
 * 
 * 
 */

@Controller
public class erGerenciasController {

	@Autowired
	private erGerenciasService ErGerenciasService;
	//se define el mapeo de las gerencias(esta no se dice que es una url porque no tiene una pagina html dentro de ECOrisk)
	@GetMapping("/config/gerencias")
	public String getEtapas(Model model) {
		//se crea una lista de gerencias y se define el atributo de modelo para que puedan ser usadas cuando se muestran en alguna plantilla html 
		List<erGerencias> gerenciasList = ErGerenciasService.getGerencias();
		model.addAttribute("gerenciasList",gerenciasList);
		return "gerencias";
	}
}

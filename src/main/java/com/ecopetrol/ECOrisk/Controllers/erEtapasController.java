package com.ecopetrol.ECOrisk.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecopetrol.ECOrisk.Models.erEtapas;
import com.ecopetrol.ECOrisk.Services.erEtapasService;

@Controller
public class erEtapasController {

	@Autowired
	private erEtapasService ErEtapasService;
	
	@GetMapping("/config/etapas")
	public String getEtapas(Model model) {
		
		List<erEtapas> etapasList = ErEtapasService.getEtapas();
		model.addAttribute("etapasList",etapasList);
		return "etapas";
	}
	
	erEtapas etapa1 = new erEtapas();
	
}

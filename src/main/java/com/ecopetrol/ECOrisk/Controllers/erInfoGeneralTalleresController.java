package com.ecopetrol.ECOrisk.Controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.ecopetrol.ECOrisk.Models.erInfoGeneralTalleres;


import com.ecopetrol.ECOrisk.Services.erInfoGeneralTalleresService;



/**
 * 
 * controlador del protafolio de proyectos CAPEX
 * @author Manuel Eduardo Patarroyo Santos
 * 
 * 
 */


@Controller
public class erInfoGeneralTalleresController {
	
	@Autowired
	private erInfoGeneralTalleresService erInfoGeneralTalleresService;



	/*
	 * //se definen todos los atributos de un proyecto y se retornan los valores 
	 * , esto para la pagina de edicion de elementos
	 */	
	@GetMapping("/InfoGeneralTalleres")
	public String gettInfoGeneralTalleres(Model model, Principal principal) {
	
		model.addAttribute("infopro", erInfoGeneralTalleresService.encontrartodo());
		
		return "InfoGeneralTalleres";
	}
	//Funcion para llamar los items existentes 
	@RequestMapping("InfoGeneralTalleres/findById") 
	@ResponseBody
	public Optional<erInfoGeneralTalleres> findById(Integer id)
	{
		return erInfoGeneralTalleresService.findById(id);
	}
	
	//Funcion para crear un nuevo item 
	
	@PostMapping(value="InfoGeneralTalleres/addNew")
	public String addNew(erInfoGeneralTalleres InfoGeneralTalleres) {
		erInfoGeneralTalleresService.save(InfoGeneralTalleres);
		return "redirect:/InfoGeneralTalleres";
	}
	//Funcion para actualizar los datos de un elemento se utiliza requestmethod para realizar los cambios y llamar los datosz
	@RequestMapping(value="InfoGeneralTalleres/update", method = {RequestMethod.PUT, RequestMethod.GET})
	public String update(erInfoGeneralTalleres InfoGeneralTalleres) {
		erInfoGeneralTalleresService.save(InfoGeneralTalleres);
		return "redirect:/InfoGeneralTalleres";
	}
	//Funcion para eliminar un elemento se utiliza requestmethod para realizar los cambios y llamar los datosz
	@RequestMapping(value="InfoGeneralTalleres/delete", method = {RequestMethod.DELETE, RequestMethod.GET})	
	public String delete(Integer id) {
		erInfoGeneralTalleresService.delete(id);
		return "redirect:/InfoGeneralTalleres";
	}
	


}

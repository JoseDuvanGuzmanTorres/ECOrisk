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

import com.ecopetrol.ECOrisk.Models.erEstadopro;
import com.ecopetrol.ECOrisk.Models.erPortafolio;
import com.ecopetrol.ECOrisk.Models.erProceso;
import com.ecopetrol.ECOrisk.Services.erEstadoproyectoService;
import com.ecopetrol.ECOrisk.Services.erPortafolioService;
import com.ecopetrol.ECOrisk.Services.erProcesoService;


/**
 * 
 * controlador del protafolio de proyectos CAPEX
 * @author Manuel Eduardo Patarroyo Santos
 * 
 * 
 */


@Controller
public class erPortafolioController {
	
	@Autowired
	private erPortafolioService ErPortafolioService;
	@Autowired
	private erProcesoService ErProcesoService;
	@Autowired
	private erEstadoproyectoService erEstadoproyectoService;

	/*
	 * //se definen todos los atributos de un proyecto y se retornan los valores al
	 * protafolio, esto para la pagina de edicion de elementos del portafolio
	 */	
	@GetMapping("/portafolio")
	public String Portafolio(Model model,Principal principal) {
		List<erPortafolio> portafolioc = ErPortafolioService.findAll();
		List<erEstadopro> faseproyecto = erEstadoproyectoService.getEstadoproyecto();
		List<erProceso> procesos = ErProcesoService.getProcesos();
		model.addAttribute("portafolioc", portafolioc);
		model.addAttribute("procesos", procesos);
		model.addAttribute("faseproyecto", faseproyecto);
		return "portafolio";
	}
	//Funcion para llamar los items existentes del portafolio
	@RequestMapping("portafolio/findById") 
	@ResponseBody
	public Optional<erPortafolio> findById(Integer id)
	{
		return ErPortafolioService.findById(id);
	}
	
	//Funcion para crear un nuevo item en el portafolio
	
	@PostMapping(value="portafolio/addNew")
	public String addNew(erPortafolio portafolio) {
		ErPortafolioService.save(portafolio);
		return "redirect:/portafolio";
	}
	//Funcion para actualizar los datos de un elemento en el portafolio se utiliza requestmethod para realizar los cambios y llamar los datosz
	@RequestMapping(value="portafolio/update", method = {RequestMethod.PUT, RequestMethod.GET})
	public String update(erPortafolio portafolio) {
		ErPortafolioService.save(portafolio);
		return "redirect:/portafolio";
	}
	//Funcion para eliminar un elemento del portafolio se utiliza requestmethod para realizar los cambios y llamar los datosz
	@RequestMapping(value="portafolio/delete", method = {RequestMethod.DELETE, RequestMethod.GET})	
	public String delete(Integer id) {
		ErPortafolioService.delete(id);
		return "redirect:/portafolio";
	}
	//Pagina de consulta de el protafolio CAPEX
	@GetMapping("/portafolio-capex")
	public String PortafolioCapex(Model model,Principal principal) {
		List<erPortafolio> portafolioc = ErPortafolioService.loadPortafolioByProcesoId(1);
		model.addAttribute("portafolioc", portafolioc);

		return "portafolio-capex";
	}


}

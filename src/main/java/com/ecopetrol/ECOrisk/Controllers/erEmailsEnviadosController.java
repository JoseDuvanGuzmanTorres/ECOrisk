package com.ecopetrol.ECOrisk.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Projections.erEmailsEnviadosProjection;
import com.ecopetrol.ECOrisk.Services.UserService;
import com.ecopetrol.ECOrisk.Services.erEmailsEnviadosService;


/**
 * 
 * Se inizializa el controlador de los emails enviados para su seguimiento
 * @author Manuel Eduardo Patarroyo Santos
 * 
 * 
 */

@Controller
public class erEmailsEnviadosController {

	@Autowired
	private erEmailsEnviadosService ErEmailsEnviadosService;
	@Autowired
	private UserService userService;
	
	/*
	 * Se define la runa que utilizara la pagina de seguimiento de los emails que se
	 * envian
	 */
	
	@GetMapping("/load/emails-enviados")
	public String getEmails(Model model) {
		
		//se trae la lista de usuarios, se crea la de emails enviados y se relacionan para poder identificar el correo y asignarle el usuario a que fue enviado
		List<Users> usersList = userService.findAll();
		List<erEmailsEnviadosProjection> emailsList = ErEmailsEnviadosService.getEmailsProjection();
		model.addAttribute("emailsList",emailsList);
		model.addAttribute("usersList",usersList);
		return "emails";
	}

	
}

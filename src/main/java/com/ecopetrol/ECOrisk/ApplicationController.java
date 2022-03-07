package com.ecopetrol.ECOrisk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Projections.ContadorTalleresProjection;
import com.ecopetrol.ECOrisk.Projections.UsersProjection;
import com.ecopetrol.ECOrisk.Projections.erRiesgos_ValoracionProjection;
import com.ecopetrol.ECOrisk.Services.UserService;
import com.ecopetrol.ECOrisk.Services.erEncabezadoService;
import com.ecopetrol.ECOrisk.Services.erRiesgos_ValoracionService;

@Controller
public class ApplicationController {

/**
 * ApplicationController inicializa los controladores principales y se configura la pagina index de ECOrisk
 * 
 * @author Manuel Eduardo Patarroyo Santos
 * @coauthor José Duvan Guzmán Torres
 * 
 */
	//se llaman las clases que se utilizaran, en este caso los servicios del encabezado, el de usuarios(user) y riesgos valoracion.
	@Autowired
	private erEncabezadoService Er_EncabezadoService;
	@Autowired
	private UserService userService;
	@Autowired
	private erRiesgos_ValoracionService ErRiesgos_ValoracionService;
	@GetMapping("/")
	public String goHome(Model model) {

		/**
		 * GoHome declara las variables y modelos para los datos del dashboard
		 */
		
		erRiesgos_ValoracionProjection valoraciones = ErRiesgos_ValoracionService.getValoracionesProjection();

		Integer InicialCapexN = valoraciones.getInicialCapexN();
		Integer InicialCapexL = valoraciones.getInicialCapexL();
		Integer InicialCapexM = valoraciones.getInicialCapexM();
		Integer InicialCapexH = valoraciones.getInicialCapexH();
		Integer InicialCapexVH = valoraciones.getInicialCapexVH();
		Integer ResidualCapexN = valoraciones.getResidualCapexN();
		Integer ResidualCapexL = valoraciones.getResidualCapexL();
		Integer ResidualCapexM = valoraciones.getResidualCapexM();
		Integer ResidualCapexH = valoraciones.getResidualCapexH();
		Integer ResidualCapexVH = valoraciones.getResidualCapexVH();
		Integer CerradosCapexN = valoraciones.getCerradosCapexN();
		Integer CerradosCapexL = valoraciones.getCerradosCapexL();
		Integer CerradosCapexM = valoraciones.getCerradosCapexM();
		Integer CerradosCapexH = valoraciones.getCerradosCapexH();
		Integer CerradosCapexVH = valoraciones.getCerradosCapexVH();
		Integer AbiertosCapexN = valoraciones.getAbiertosCapexN();
		Integer AbiertosCapexL = valoraciones.getAbiertosCapexL();
		Integer AbiertosCapexM = valoraciones.getAbiertosCapexM();
		Integer AbiertosCapexH = valoraciones.getAbiertosCapexH();
		Integer AbiertosCapexVH = valoraciones.getAbiertosCapexVH();
		Integer CongeladosCapexVH = valoraciones.getCongeladosCapexVH();
		Integer CongeladosCapexH = valoraciones.getCongeladosCapexH();
		Integer CongeladosCapexM = valoraciones.getCongeladosCapexM();
		Integer CongeladosCapexL = valoraciones.getCongeladosCapexL();
		Integer CongeladosCapexN = valoraciones.getCongeladosCapexN();
		Integer Cerradoauca1 = valoraciones.getCerradoauca();
		Integer InicialOpexN = valoraciones.getInicialOpexN();
		Integer InicialOpexL = valoraciones.getInicialOpexL();
		Integer InicialOpexM = valoraciones.getInicialOpexM();
		Integer InicialOpexH = valoraciones.getInicialOpexH();
		Integer InicialOpexVH = valoraciones.getInicialOpexVH();
		Integer ResidualOpexN = valoraciones.getResidualOpexN();
		Integer ResidualOpexL = valoraciones.getResidualOpexL();
		Integer ResidualOpexM = valoraciones.getResidualOpexM();
		Integer ResidualOpexH = valoraciones.getResidualOpexH();
		Integer ResidualOpexVH = valoraciones.getResidualOpexVH();
		Integer CerradosOpexN = valoraciones.getCerradosOpexN();
		Integer CerradosOpexL = valoraciones.getCerradosOpexL();
		Integer CerradosOpexM = valoraciones.getCerradosOpexM();
		Integer CerradosOpexH = valoraciones.getCerradosOpexH();
		Integer CerradosOpexVH = valoraciones.getCerradosOpexVH();
		Integer AbiertosOpexN = valoraciones.getAbiertosOpexN();
		Integer AbiertosOpexL = valoraciones.getAbiertosOpexL();
		Integer AbiertosOpexM = valoraciones.getAbiertosOpexM();
		Integer AbiertosOpexH = valoraciones.getAbiertosOpexH();
		Integer AbiertosOpexVH = valoraciones.getAbiertosOpexVH();
		Integer CongeladosOpexVH = valoraciones.getCongeladosOpexVH();
		Integer CongeladosOpexH = valoraciones.getCongeladosOpexH();
		Integer CongeladosOpexM = valoraciones.getCongeladosOpexM();
		Integer CongeladosOpexL = valoraciones.getCongeladosOpexL();
		Integer CongeladosOpexN = valoraciones.getCongeladosOpexN();
		Integer CerradAutoCapeHV = valoraciones.getCerradAutoCapeHV();
		Integer CerradAutoCapexH = valoraciones.getCerradAutoCapexH();
		Integer CerradAutoCapexM = valoraciones.getCerradAutoCapexM();
		Integer CerradAutoCapexL = valoraciones.getCerradAutoCapexL();
		Integer CerradAutoCapexN = valoraciones.getCerradAutoCapexN();
		Integer CerradAutoOpexHV = valoraciones.getCerradAutoOpexHV();
		Integer CerradAutoOpexH = valoraciones.getCerradAutoOpexH();
		Integer CerradAutoOpexM = valoraciones.getCerradAutoOpexM();
		Integer CerradAutoOpexL = valoraciones.getCerradAutoOpexL();
		Integer CerradAutoOpexN = valoraciones.getCerradAutoOpexN();
		Integer TotalInicialCapex = valoraciones.getTotalInicialCapex();
		Integer TotalAbiertosCapex = valoraciones.getTotalAbiertosCapex();
		Integer TotalCerradosCapex = valoraciones.getTotalCerradosCapex();
		Integer TotalCongeladosCapex = valoraciones.getTotalCongeladosCapex();
		Integer TotalInicialOpex = valoraciones.getTotalInicialOpex();
		Integer TotalAbiertosOpex = valoraciones.getTotalAbiertosOpex();
		Integer TotalCerradosOpex = valoraciones.getTotalCerradosOpex();
		Integer TotalCongeladosOpex = valoraciones.getTotalCongeladosOpex();

		model.addAttribute("InicialCapexN", InicialCapexN);
		model.addAttribute("InicialCapexL", InicialCapexL);
		model.addAttribute("InicialCapexM", InicialCapexM);
		model.addAttribute("InicialCapexH", InicialCapexH);
		model.addAttribute("InicialCapexVH", InicialCapexVH);
		model.addAttribute("ResidualCapexN", ResidualCapexN);
		model.addAttribute("ResidualCapexL", ResidualCapexL);
		model.addAttribute("ResidualCapexM", ResidualCapexM);
		model.addAttribute("ResidualCapexH", ResidualCapexH);
		model.addAttribute("ResidualCapexVH", ResidualCapexVH);
		model.addAttribute("CerradosCapexN", CerradosCapexN);
		model.addAttribute("CerradosCapexL", CerradosCapexL);
		model.addAttribute("CerradosCapexM", CerradosCapexM);
		model.addAttribute("CerradosCapexH", CerradosCapexH);
		model.addAttribute("CerradosCapexVH", CerradosCapexVH);
		model.addAttribute("AbiertosCapexN", AbiertosCapexN);
		model.addAttribute("AbiertosCapexL", AbiertosCapexL);
		model.addAttribute("AbiertosCapexM", AbiertosCapexM);
		model.addAttribute("AbiertosCapexH", AbiertosCapexH);
		model.addAttribute("AbiertosCapexVH", AbiertosCapexVH);
		model.addAttribute("CongeladosCapexVH", CongeladosCapexVH);
		model.addAttribute("CongeladosCapexH", CongeladosCapexH);
		model.addAttribute("CongeladosCapexM", CongeladosCapexM);
		model.addAttribute("CongeladosCapexL", CongeladosCapexL);
		model.addAttribute("CongeladosCapexN", CongeladosCapexN);
		model.addAttribute("InicialOpexN", InicialOpexN);
		model.addAttribute("InicialOpexL", InicialOpexL);
		model.addAttribute("InicialOpexM", InicialOpexM);
		model.addAttribute("InicialOpexH", InicialOpexH);
		model.addAttribute("InicialOpexVH", InicialOpexVH);
		model.addAttribute("ResidualOpexN", ResidualOpexN);
		model.addAttribute("ResidualOpexL", ResidualOpexL);
		model.addAttribute("ResidualOpexM", ResidualOpexM);
		model.addAttribute("ResidualOpexH", ResidualOpexH);
		model.addAttribute("ResidualOpexVH", ResidualOpexVH);
		model.addAttribute("CerradosOpexN", CerradosOpexN);
		model.addAttribute("CerradosOpexL", CerradosOpexL);
		model.addAttribute("CerradosOpexM", CerradosOpexM);
		model.addAttribute("CerradosOpexH", CerradosOpexH);
		model.addAttribute("CerradosOpexVH", CerradosOpexVH);
		model.addAttribute("AbiertosOpexN", AbiertosOpexN);
		model.addAttribute("AbiertosOpexL", AbiertosOpexL);
		model.addAttribute("AbiertosOpexM", AbiertosOpexM);
		model.addAttribute("AbiertosOpexH", AbiertosOpexH);
		model.addAttribute("AbiertosOpexVH", AbiertosOpexVH);
		model.addAttribute("CongeladosOpexVH", CongeladosOpexVH);
		model.addAttribute("CongeladosOpexH", CongeladosOpexH);
		model.addAttribute("CongeladosOpexM", CongeladosOpexM);
		model.addAttribute("CongeladosOpexL", CongeladosOpexL);
		model.addAttribute("CongeladosOpexN", CongeladosOpexN);
		model.addAttribute("CerradAutoCapeHV", CerradAutoCapeHV);
		model.addAttribute("CerradAutoCapexH", CerradAutoCapexH);
		model.addAttribute("CerradAutoCapexM", CerradAutoCapexM);
		model.addAttribute("CerradAutoCapexL", CerradAutoCapexL);
		model.addAttribute("CerradAutoCapexN", CerradAutoCapexN);
		model.addAttribute("CerradAutoOpexHV", CerradAutoOpexHV);
		model.addAttribute("CerradAutoOpexH", CerradAutoOpexH);
		model.addAttribute("CerradAutoOpexM", CerradAutoOpexM);
		model.addAttribute("CerradAutoOpexL", CerradAutoOpexL);
		model.addAttribute("CerradAutoOpexN", CerradAutoOpexN);
		model.addAttribute("TotalInicialCapex", TotalInicialCapex);
		model.addAttribute("TotalAbiertosCapex", TotalAbiertosCapex);
		model.addAttribute("TotalCerradosCapex", TotalCerradosCapex);
		model.addAttribute("TotalCongeladosCapex", TotalCongeladosCapex);
		model.addAttribute("TotalInicialOpex", TotalInicialOpex);
		model.addAttribute("TotalAbiertosOpex", TotalAbiertosOpex);
		model.addAttribute("TotalCerradosOpex", TotalCerradosOpex);
		model.addAttribute("TotalCongeladosOpex", TotalCongeladosOpex);

		ContadorTalleresProjection contadores = Er_EncabezadoService.getContadorTalleres();
		Integer Whatifca = contadores.getWhatifca();
		Integer Whatifop = contadores.getWhatifop();
		Integer Construca = contadores.getConstruca();
		Integer Construop = contadores.getConstruop();
		Integer Desreca = contadores.getDesreca();
		Integer Desreop = contadores.getDesreop();
		Integer Peerreca = contadores.getPeerreca();
		Integer Peerreop = contadores.getPeerreop();
		Integer LeccionesAca = contadores.getLeccionesAca();
		Integer LeccionesAop = contadores.getLeccionesAop();

		model.addAttribute("Whatifca", Whatifca);
		model.addAttribute("Whatifop", Whatifop);
		model.addAttribute("Construca", Construca);
		model.addAttribute("Construop", Construop);
		model.addAttribute("Desreca", Desreca);
		model.addAttribute("Desreop", Desreop);
		model.addAttribute("Peerreca", Peerreca);
		model.addAttribute("Peerreop", Peerreop);
		model.addAttribute("LeccionesAca", LeccionesAca);
		model.addAttribute("LeccionesAop", LeccionesAop);
		
		List<UsersProjection> usuarios = userService.getUserProjection(); //se obtiene la lista de usuarios y se devuebe el total de usuarios 	
		model.addAttribute("usuarios", usuarios);
		Integer contauser = usuarios.size();
		model.addAttribute("contauser", contauser);

		return "index";
	}
	
	// a continuacion se declaran las url de las paginas /acerca-de, /contactenos, /login, /logut, /register
	
	@GetMapping("/acerca-de")
	public String goAcercaDe() {
		return "acercade";
	}

	@GetMapping("/contactenos")
	public String goContactenos() {
		return "contactenos";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}
}

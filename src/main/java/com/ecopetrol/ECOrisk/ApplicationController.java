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
	
	@Autowired
	private erEncabezadoService Er_EncabezadoService;
	@Autowired
	private UserService userService;
	@Autowired
	private erRiesgos_ValoracionService ErRiesgos_ValoracionService;
	
	
	@GetMapping("/")
	public String goHome(Model model) {
		
		erRiesgos_ValoracionProjection valoraciones = ErRiesgos_ValoracionService.getValoracionesProjection();
		
		Integer InicialCapexN   = valoraciones.getInicialCapexN();
		Integer InicialCapexL   = valoraciones.getInicialCapexL();
		Integer InicialCapexM   = valoraciones.getInicialCapexM();
		Integer InicialCapexH   = valoraciones.getInicialCapexH();
		Integer InicialCapexVH  = valoraciones.getInicialCapexVH();
		
		Integer ResidualCapexN   = valoraciones.getResidualCapexN();
		Integer ResidualCapexL   = valoraciones.getResidualCapexL();
		Integer ResidualCapexM   = valoraciones.getResidualCapexM();
		Integer ResidualCapexH   = valoraciones.getResidualCapexH();
		Integer ResidualCapexVH  = valoraciones.getResidualCapexVH();

		Integer CerradosN  = valoraciones.getCerradosN();
		Integer CerradosL  = valoraciones.getCerradosL();
		Integer CerradosM  = valoraciones.getCerradosM();
		Integer CerradosH  = valoraciones.getCerradosH();
		Integer CerradosVH = valoraciones.getCerradosVH();
		
		Integer AbiertosN = valoraciones.getAbiertosN();
		Integer AbiertosL = valoraciones.getAbiertosL();
		Integer AbiertosM = valoraciones.getAbiertosM();
		Integer AbiertosH = valoraciones.getAbiertosH();
		Integer AbiertosVH = valoraciones.getAbiertosVH();
	
		Integer CongeladosVH = valoraciones.getCongeladosVH();
		Integer CongeladosH = valoraciones.getCongeladosH();
		Integer CongeladosM = valoraciones.getCongeladosM();
		Integer CongeladosL = valoraciones.getCongeladosL();
		Integer CongeladosN = valoraciones.getCongeladosN();
		
		model.addAttribute("InicialCapexN",   InicialCapexN);
		model.addAttribute("InicialCapexL",   InicialCapexL);
		model.addAttribute("InicialCapexM",   InicialCapexM);
		model.addAttribute("InicialCapexH",   InicialCapexH);
		model.addAttribute("InicialCapexVH",  InicialCapexVH);
		
		model.addAttribute("ResidualCapexN",   ResidualCapexN);
		model.addAttribute("ResidualCapexL",   ResidualCapexL);
		model.addAttribute("ResidualCapexM",   ResidualCapexM);
		model.addAttribute("ResidualCapexH",   ResidualCapexH);
		model.addAttribute("ResidualCapexVH",  ResidualCapexVH);
		
		model.addAttribute("CerradosN",  CerradosN);
		model.addAttribute("CerradosL",  CerradosL);
		model.addAttribute("CerradosM",  CerradosM);
		model.addAttribute("CerradosH",  CerradosH);
		model.addAttribute("CerradosVH", CerradosVH);
			
		model.addAttribute("AbiertosN", AbiertosN);
		model.addAttribute("AbiertosL", AbiertosL);
		model.addAttribute("AbiertosM", AbiertosM);
		model.addAttribute("AbiertosH", AbiertosH);
		model.addAttribute("AbiertosVH", AbiertosVH);
		
		model.addAttribute("CongeladosVH", CongeladosVH);
		model.addAttribute("CongeladosH", CongeladosH);
		model.addAttribute("CongeladosM", CongeladosM);
		model.addAttribute("CongeladosL", CongeladosL);
		model.addAttribute("CongeladosN", CongeladosN);
		
		
		
		Integer ORaN   = valoraciones.getORaN();
		Integer ORaL   = valoraciones.getORaL();
		Integer ORaM   = valoraciones.getORaM();
		Integer ORaH   = valoraciones.getORaH();
		Integer ORaVH  = valoraciones.getORaVH();
		Integer ORrN   = valoraciones.getORrN();
		Integer ORrL   = valoraciones.getORrL();
		Integer ORrM   = valoraciones.getORrM();
		Integer ORrH   = valoraciones.getORrH();
		Integer ORrVH  = valoraciones.getORrVH();
		Integer ORraN  = valoraciones.getORraN();
		Integer ORraL  = valoraciones.getORraL();
		Integer ORraM  = valoraciones.getORraM();
		Integer ORraH  = valoraciones.getORraH();
		
		model.addAttribute("ORaN",   ORaN);
		model.addAttribute("ORaL",   ORaL);
		model.addAttribute("ORaM",   ORaM);
		model.addAttribute("ORaH",   ORaH);
		model.addAttribute("ORaVH",  ORaVH);
		model.addAttribute("ORrN",   ORrN);
		model.addAttribute("ORrL",   ORrL);
		model.addAttribute("ORrM",   ORrM);
		model.addAttribute("ORrH",   ORrH);
		model.addAttribute("ORrVH",  ORrVH);
		model.addAttribute("ORraN",  ORraN);
		model.addAttribute("ORraL",  ORraL);
		model.addAttribute("ORraM",  ORraM);
		model.addAttribute("ORraH",  ORraH);
		
		
		
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
		
		List<UsersProjection> usuarios = userService.getUserProjection();
		model.addAttribute("usuarios", usuarios);
		Integer contauser = usuarios.size();
		model.addAttribute("contauser", contauser);
		
		return "index";
	}
	
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

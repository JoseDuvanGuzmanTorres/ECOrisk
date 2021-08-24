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
		
		Integer CRaN   = valoraciones.getCRaN();
		Integer CRaL   = valoraciones.getCRaL();
		Integer CRaM   = valoraciones.getCRaM();
		Integer CRaH   = valoraciones.getCRaH();
		Integer CRaVH  = valoraciones.getCRaVH();
		Integer CRrN   = valoraciones.getCRrN();
		Integer CRrL   = valoraciones.getCRrL();
		Integer CRrM   = valoraciones.getCRrM();
		Integer CRrH   = valoraciones.getCRrH();
		Integer CRrVH  = valoraciones.getCRrVH();
		Integer CRraN  = valoraciones.getCRraN();
		Integer CRraL  = valoraciones.getCRraL();
		Integer CRraM  = valoraciones.getCRraM();
		Integer CRraH  = valoraciones.getCRraH();
		Integer CRraVH = valoraciones.getCRraVH();
		Integer CRrcVH = valoraciones.getCRrcVH();//congelados vh capex
		
		Integer CRrcH = valoraciones.getCRrcH();
		Integer CRrcM = valoraciones.getCRrcM();
		Integer CRrcL = valoraciones.getCRrcL();
		Integer CRrcN = valoraciones.getCRrcN();
		Integer CRrinVH = valoraciones.getCRrinVH();//inicial vh
	
		
		model.addAttribute("CRaN",   CRaN);
		model.addAttribute("CRaL",   CRaL);
		model.addAttribute("CRaM",   CRaM);
		model.addAttribute("CRaH",   CRaH);
		model.addAttribute("CRaVH",  CRaVH);
		
		model.addAttribute("CRrN",   CRrN);
		model.addAttribute("CRrL",   CRrL);
		model.addAttribute("CRrM",   CRrM);
		model.addAttribute("CRrH",   CRrH);
		model.addAttribute("CRrVH",  CRrVH);
		
		model.addAttribute("CRraN",  CRraN);
		model.addAttribute("CRraL",  CRraL);
		model.addAttribute("CRraM",  CRraM);
		model.addAttribute("CRraH",  CRraH);
		model.addAttribute("CRraVH", CRraVH);
		model.addAttribute("CRrcVH", CRrcVH);
		
		model.addAttribute("CRrcH", CRrcH);
		model.addAttribute("CRrcM", CRrcM);
		model.addAttribute("CRrcL", CRrcL);
		model.addAttribute("CRrcN", CRrcN);

		model.addAttribute("CRrinVH", CRrinVH);
		
		
		
		
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

package com.ecopetrol.ECOrisk.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecopetrol.ECOrisk.Services.UploadService;

@Controller
public class CargarController {

	@Autowired
	private UploadService uploadService;
	
	public void UploadController(UploadService uploadService) {
		this.uploadService = uploadService;
	}
	
	@GetMapping("/upload/whatif")
	public String uploadWhatIf() {
		return "uploadwhatif";
	}
	
	@GetMapping("/upload/constructabilidad")
	public String uploadConstructabilidad() {
		return "uploadconstruc";
	}
	
	@GetMapping("/upload/designreview")
	public String uploadDesignReview() {
		return "uploaddesignr";
	}
	
	@GetMapping("/upload/peerreview")
	public String uploadPeerReview() {
		return "uploadpeerr";
	}
	
	@GetMapping("/upload/riesgosem")
	public String uploadRiesgosEmergentes() {
		return "uploadriesgos";
	}
	
	@GetMapping("/upload/lecciones")
	public String uploadLeccionesAprendidas() {
		return "uploadlecciones";
	}
	
	@GetMapping("/upload/riesgosma")
	public String uploadRiesgosMaterializados() {
		return "uploadriesgosma";
	}

	@PostMapping("/upload/constructabilidad-")
	public String upload_Constructabilidad(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		
		List<String> Errores = uploadService.saveDataFromUploadFile(file,2);
		if(Errores.isEmpty()) {
			redirectAttributes.addFlashAttribute("successmessage", "Constructabilidad subido correctamente");
		}else {
			redirectAttributes.addFlashAttribute("errormessage", Errores);
		}
		return "redirect:/consultar";
	}
	
	@PostMapping("/upload/what-if")
	public String upload_WhatIf(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		
		List<String> Errores = uploadService.saveDataFromUploadFile(file,1);
		if(Errores.isEmpty()) {
			redirectAttributes.addFlashAttribute("successmessage", "What-IF subido correctamente");
		}else {
			redirectAttributes.addFlashAttribute("errormessage", Errores);
		}
		return "redirect:/consultar";
	}
	
	@PostMapping("/upload/design-review")
	public String upload_DesingReview(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		
		List<String> Errores = uploadService.saveDataFromUploadFile(file,3);
		if(Errores.isEmpty()) {
			redirectAttributes.addFlashAttribute("successmessage", "Design Review subido correctamente");
		}else {
			redirectAttributes.addFlashAttribute("errormessage", Errores);
		}
		return "redirect:/consultar";
	}
	
	@PostMapping("/upload/peer-review")
	public String upload_PeerReview(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		
		List<String> Errores = uploadService.saveDataFromUploadFile(file,4);
		if(Errores.isEmpty()) {
			redirectAttributes.addFlashAttribute("successmessage", "Peer Review subido correctamente");
		}else {
			redirectAttributes.addFlashAttribute("errormessage", Errores);
		}
		return "redirect:/consultar";
	}
	
	@PostMapping("/upload/riesgos-emergentes")
	public String upload_RiesgosEmergentes(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		List<String> Errores = uploadService.saveDataFromUploadFile(file,5);
		if(Errores.isEmpty()) {
			redirectAttributes.addFlashAttribute("successmessage", "Riesgos emergentes subido correctamente");
		}else {
			redirectAttributes.addFlashAttribute("errormessage", Errores);
		}
		return "redirect:/consultar";
	}
	
	@PostMapping("/upload/lecciones-aprendidas")
	public String upload_LeccionesAprendidas(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		List<String> Errores = uploadService.saveDataFromUploadFile(file,6);
		if(Errores.isEmpty()) {
			redirectAttributes.addFlashAttribute("successmessage", "Lecciones Aprendidas subido correctamente");
		}else {
			redirectAttributes.addFlashAttribute("errormessage", Errores);
		}
		return "redirect:/consultar";
	}
	
	@PostMapping("/upload/riesgos-materializados")
	public String upload_RiesgosMaterializados(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		List<String> Errores = uploadService.saveDataFromUploadFile(file,7);
		if(Errores.isEmpty()) {
			redirectAttributes.addFlashAttribute("successmessage", "Riesgos Materializados subido correctamente");
		}else {
			redirectAttributes.addFlashAttribute("errormessage", Errores);
		}
		return "redirect:/consultar";
	}
	
}

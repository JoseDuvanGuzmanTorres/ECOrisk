package com.ecopetrol.ECOrisk.Controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Models.erCambios;
import com.ecopetrol.ECOrisk.Models.erEncabezado;
import com.ecopetrol.ECOrisk.Models.er_HojaSeguimiento;
import com.ecopetrol.ECOrisk.Models.er_HojaTrabajo;
import com.ecopetrol.ECOrisk.Projections.erCambiosProjection;
import com.ecopetrol.ECOrisk.Services.UserService;
import com.ecopetrol.ECOrisk.Services.erCambiosService;
import com.ecopetrol.ECOrisk.Services.erEncabezadoService;
import com.ecopetrol.ECOrisk.Services.er_HojaSeguimientoService;
import com.ecopetrol.ECOrisk.Services.er_HojaTrabajoService;


@Controller
public class CambiosController {
	@Autowired
	private er_HojaSeguimientoService Er_HojaSeguimientoService;
	
	@Autowired
	private erEncabezadoService ErEncabezadoService;
	
	@Autowired
	private er_HojaTrabajoService Er_HojaTrabajoService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private erCambiosService ErCambiosService;
	
	@RequestMapping("cambios/findByTrabajoId")
	@ResponseBody
	public List<erCambiosProjection> findByTrabajoId(int hojatrabajo_id) {
		return ErCambiosService.getAllProjectionByIdEncabeAndUserId(hojatrabajo_id);
	}
	
	//public static String uploadDirectory = System.getProperty("user.home")+File.separator+"uploads"+File.separator+"cambios";
	
	public static String uploadDirectory = "/home/uploads"+File.separator+"cambios";

	@RequestMapping(value=("cambiogeneral/addNew"),headers=("content-type=multipart/*"),method=RequestMethod.POST)
	public String addNew(@RequestParam("evidencia") MultipartFile[] file, @RequestParam Integer de,@RequestParam Integer[] cuales, @RequestParam Integer a, @RequestParam String desc, Principal principal, RedirectAttributes redirectAttributes){
		String username = principal.getName();
		Users user = userService.loadUserByUsername(username);
		
		String direccion = "";
		String ruta = "";
		String archi1 = "";
		String archi2 = "";
		String archi3 = "";
		boolean vacio = false;
		erCambios nuevoCambio = new erCambios();

		if(file.length<4) {
			int conta = 0;
			for(MultipartFile archi:file) {
				//Path fileNameAndPath = Paths.get(uploadDirectory, archi.getOriginalFilename());
				
				if(!(archi.isEmpty())){
					if(conta ==0) {
						nuevoCambio.setEr_olduser(de);
						nuevoCambio.setEr_newuser(a);
						ErCambiosService.save(nuevoCambio);
					}
					
					Path directorio = Paths.get(uploadDirectory+File.separator+nuevoCambio.getEr_cambio_id());
					
					String rutaabsoluta = directorio.toFile().getAbsolutePath();
					
					ruta = "/uploads/cambios/"+nuevoCambio.getEr_cambio_id()+"/";
					try {
						direccion = rutaabsoluta+File.separator+archi.getOriginalFilename();
						Path rutacompleta = Paths.get(direccion);
						Files.createDirectories(directorio);
						Files.write(rutacompleta, archi.getBytes());
						if(conta==0) {
							archi1 = archi.getOriginalFilename();
						}else if(conta==1) {
							archi2 = archi.getOriginalFilename();
						}else {
							archi3 = archi.getOriginalFilename();
						}
						
					}catch(Exception e) {
						e.printStackTrace();
					}
				}else {
					if(conta ==0) {
						vacio = true;
					}
				}
				
				conta++;
				
			}
		}
		
		if(!vacio) {
			
			List<Integer> encabezados_id = new ArrayList<Integer>();
			boolean todos = false;
			for(Integer cual :cuales) {
				if(cual == 0) {
					todos = true;
				}else {
					encabezados_id.add(cual);
				}
				
			}
			List<er_HojaTrabajo> controles = new ArrayList<er_HojaTrabajo>();
			if(todos) {
				controles = Er_HojaTrabajoService.getHojaTrabajoByFuncionario(de);
			}else {
				controles = Er_HojaTrabajoService.getHojaByEncabeIdListAndUserId(encabezados_id,de);
			}
			
			
			int contador = 0;
			Date date = new Date();
			er_HojaSeguimiento seguimiento = new er_HojaSeguimiento();
			seguimiento.setHtseg_fechaescrita(date);
			seguimiento.setHtseg_comentario(desc);
			seguimiento.setHtseg_ruta(ruta);
			seguimiento.setUser_id(user.getId());
			seguimiento.setEr_cambio_id(nuevoCambio.getEr_cambio_id());
			seguimiento.setHtseg_evidencia1(archi1);
			seguimiento.setHtseg_evidencia2(archi2);
			seguimiento.setHtseg_evidencia3(archi3);
			seguimiento.addAllHojaTrabajo(controles);
			Er_HojaSeguimientoService.save(seguimiento);
			for(er_HojaTrabajo control : controles) {
				control.setHt_responsableimplementacion(a);
				Er_HojaTrabajoService.save(control);
				contador++;
			}
			
			
			
			
				redirectAttributes.addFlashAttribute("successmessage", "Cambio realizado satisfactoriamente. [ "+contador+" ] Registros reasignados");
			
				return "redirect:/load/cambios";
			}
			
				redirectAttributes.addFlashAttribute("errormessage", "No cargó ninguna evidencia");
				return "redirect:/load/cambios";
		
	}

	@RequestMapping(value=("cambiodueno/addNew"),headers=("content-type=multipart/*"),method=RequestMethod.POST)
	public String addNewDueno(@RequestParam("evidenciaDue") MultipartFile[] file, @RequestParam Integer deDue ,@RequestParam Integer[] cualesDue , @RequestParam Integer aDue, @RequestParam String descDue, Principal principal, RedirectAttributes redirectAttributes){
		String username = principal.getName();
		Users user = userService.loadUserByUsername(username);
		
		String direccion = "";
		String ruta = "";
		String archi1 = "";
		String archi2 = "";
		String archi3 = "";
		boolean vacio = false;
		erCambios nuevoCambio = new erCambios();

		if(file.length<4) {
			int conta = 0;
			for(MultipartFile archi:file) {
				//Path fileNameAndPath = Paths.get(uploadDirectory, archi.getOriginalFilename());
				
				if(!(archi.isEmpty())){
					if(conta ==0) {
						nuevoCambio.setEr_olduser(deDue);
						nuevoCambio.setEr_newuser(aDue);
						ErCambiosService.save(nuevoCambio);
					}
					
					Path directorio = Paths.get(uploadDirectory+File.separator+nuevoCambio.getEr_cambio_id());
					
					String rutaabsoluta = directorio.toFile().getAbsolutePath();
					
					ruta = "/uploads/cambios/"+nuevoCambio.getEr_cambio_id()+"/";
					try {
						direccion = rutaabsoluta+File.separator+archi.getOriginalFilename();
						Path rutacompleta = Paths.get(direccion);
						Files.createDirectories(directorio);
						Files.write(rutacompleta, archi.getBytes());
						if(conta==0) {
							archi1 = archi.getOriginalFilename();
						}else if(conta==1) {
							archi2 = archi.getOriginalFilename();
						}else {
							archi3 = archi.getOriginalFilename();
						}
						
					}catch(Exception e) {
						e.printStackTrace();
					}
				}else {
					if(conta ==0) {
						vacio = true;
					}
				}
				
				conta++;
				
			}
		}
		
		if(!vacio) {
			
			List<Integer> encabezados_id = new ArrayList<Integer>();
			List<erEncabezado> encabezados = new ArrayList<erEncabezado>();
			boolean todos = false;
			for(Integer cual :cualesDue) {
				if(cual == 0) {
					todos = true;
					encabezados = ErEncabezadoService.getEncabezadosByDueno(deDue);
				}else {
					encabezados_id.add(cual);
					encabezados.add(ErEncabezadoService.getById(cual).get());
				}
				
			}
			
			List<er_HojaTrabajo> controles = new ArrayList<er_HojaTrabajo>();
			if(todos) {
				controles = Er_HojaTrabajoService.getHojaTrabajoByDuenoProyecto(deDue);
				
			}else {
				controles = Er_HojaTrabajoService.getHojaTrabajoByEncabeIdListDuenoProyecto(encabezados_id,deDue);
			}
			
			
			int contador = 0;
			Date date = new Date();
			er_HojaSeguimiento seguimiento = new er_HojaSeguimiento();
			seguimiento.setHtseg_fechaescrita(date);
			seguimiento.setHtseg_comentario(descDue);
			seguimiento.setHtseg_ruta(ruta);
			seguimiento.setUser_id(user.getId());
			seguimiento.setEr_cambio_id(nuevoCambio.getEr_cambio_id());
			seguimiento.setHtseg_evidencia1(archi1);
			seguimiento.setHtseg_evidencia2(archi2);
			seguimiento.setHtseg_evidencia3(archi3);
			seguimiento.addAllHojaTrabajo(controles);
			Er_HojaSeguimientoService.save(seguimiento);
			for(erEncabezado encabezado : encabezados) {
				encabezado.setE_liderproyecto(aDue);
				ErEncabezadoService.save(encabezado);
				contador++;
			}
			
			
			
			
				redirectAttributes.addFlashAttribute("successmessage", "Cambio realizado satisfactoriamente. [ "+contador+" ] Registros reasignados");
			
				return "redirect:/load/cambios";
			}
			
				redirectAttributes.addFlashAttribute("errormessage", "No cargó ninguna evidencia");
				return "redirect:/load/cambios";
		
	}
}


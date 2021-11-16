package com.ecopetrol.ECOrisk.Controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.ecopetrol.ECOrisk.Projections.IndicadoresProjection;
import com.ecopetrol.ECOrisk.Projections.erCambiosProjection;
import com.ecopetrol.ECOrisk.Projections.erProyectoProjection;
import com.ecopetrol.ECOrisk.Services.UserService;
import com.ecopetrol.ECOrisk.Services.erCambiosService;
import com.ecopetrol.ECOrisk.Services.erEncabezadoService;
import com.ecopetrol.ECOrisk.Services.erProyectoService;
import com.ecopetrol.ECOrisk.Services.er_HojaSeguimientoService;
import com.ecopetrol.ECOrisk.Services.er_HojaTrabajoService;


@Controller
public class CongelarController {
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
	
	@Autowired
	private erProyectoService ErProyectoService;
	
	@GetMapping("/congelar")
	public String Congelar(Model model) {
		
		List<erProyectoProjection> ProyectosList = ErProyectoService.getAllProyectoProjection();
		model.addAttribute("ProyectosList", ProyectosList);
		
		List<erProyectoProjection> CProyectosList = ErProyectoService.getAllProyectoProjection();
		model.addAttribute("CProyectosList", CProyectosList);

		return "congelar";
	}
	

	
	@RequestMapping("congelar/findByTrabajoId")
	@ResponseBody
	public List<erCambiosProjection> findByTrabajoId(int hojatrabajo_id) {
		return ErCambiosService.getAllProjectionByIdEncabeAndUserId(hojatrabajo_id);
	}
	
	//public static String uploadDirectory = System.getProperty("user.home")+File.separator+"uploads"+File.separator+"cambios";
	
	public static String uploadDirectory = "/home/uploads"+File.separator+"cambios";

	@RequestMapping(value=("congelar/addNew"),headers=("content-type=multipart/*"),method=RequestMethod.POST)
	public String addNew(@RequestParam("evidencia") MultipartFile[] file, @RequestParam String de,@RequestParam Integer[] cuales, @RequestParam String a, @RequestParam String desc, Principal principal, RedirectAttributes redirectAttributes){
		String username = principal.getName();
		Users user = userService.loadUserByUsername(username);
		
		String direccion = "";
		String ruta = "";
		String archi1 = "";
		String archi2 = "";
		String archi3 = "";
		boolean vacio = false;
		erCambios nuevoCambio = new erCambios();
		Date Fcongelamiento = new Date();
		if(file.length<4) {
			int conta = 0;
			for(MultipartFile archi:file) {
				//Path fileNameAndPath = Paths.get(uploadDirectory, archi.getOriginalFilename());
				
				if(!(archi.isEmpty())){
					if(conta ==0) {
						
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Fcongelamiento = formatter.parse(a);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						nuevoCambio.setEr_congelamiento(Fcongelamiento);
						
						ErCambiosService.save(nuevoCambio);
					}
					Path directorio = Paths.get(uploadDirectory+File.separator+nuevoCambio.getEr_cambio_id());
					
					String rutaabsoluta = directorio.toFile().getAbsolutePath();
					
					ruta = "/uploads/congelamiento/"+nuevoCambio.getEr_cambio_id()+"/";
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
			List<erEncabezado> encabezados = ErEncabezadoService.getEncabezadosByList(encabezados_id);
			
			for(erEncabezado encabezado: encabezados) {
				encabezado.setE_fechacongelamiento(Fcongelamiento);
				ErEncabezadoService.save(encabezado);
			}
			
			List<er_HojaTrabajo> controles = new ArrayList<er_HojaTrabajo>();
			if(todos) {
				controles = Er_HojaTrabajoService.getHojaTrabajoByProyectoYEstado(de,1);
			}else {
				controles = Er_HojaTrabajoService.getHojaByEncabeIdListYEstado(encabezados_id,1);
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
				control.setEr_estado_id(5);
				Date d1 = control.getHt_fechaplaneadacierre();
				Date d2 = Fcongelamiento;
				LocalDateTime date1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime date2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				
				Integer Gap = (int) (long) Duration.between(date1, date2).toDays();
				control.setGap(Gap);
				Er_HojaTrabajoService.save(control);
				contador++;
			}
			
				redirectAttributes.addFlashAttribute("successmessage", "Cierre automático realizado satisfactoriamente. [ "+contador+" ] Controles afectados");
			
				return "redirect:/congelar";
			}
			
				redirectAttributes.addFlashAttribute("errormessage", "No cargó ninguna evidencia");
				return "redirect:/congelar";
		
	}
	
	@RequestMapping(value=("descongelar/addNew"),headers=("content-type=multipart/*"),method=RequestMethod.POST)
	public String DaddNew(@RequestParam("evidencia") MultipartFile[] file, @RequestParam String de,@RequestParam Integer[] cuales, @RequestParam String a, @RequestParam String desc, Principal principal, RedirectAttributes redirectAttributes){
		String username = principal.getName();
		Users user = userService.loadUserByUsername(username);
		
		String direccion = "";
		String ruta = "";
		String archi1 = "";
		String archi2 = "";
		String archi3 = "";
		boolean vacio = false;
		erCambios nuevoCambio = new erCambios();
		Date Fdescongelamiento = new Date();
		if(file.length<4) {
			int conta = 0;
			for(MultipartFile archi:file) {
				//Path fileNameAndPath = Paths.get(uploadDirectory, archi.getOriginalFilename());
				
				if(!(archi.isEmpty())){
					if(conta ==0) {
						
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Fdescongelamiento = formatter.parse(a);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						nuevoCambio.setEr_descongelamiento(Fdescongelamiento);
						
						ErCambiosService.save(nuevoCambio);
					}
					Path directorio = Paths.get(uploadDirectory+File.separator+nuevoCambio.getEr_cambio_id());
					
					String rutaabsoluta = directorio.toFile().getAbsolutePath();
					
					ruta = "/uploads/descongelamiento/"+nuevoCambio.getEr_cambio_id()+"/";
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
			List<erEncabezado> encabezados = ErEncabezadoService.getEncabezadosByList(encabezados_id);
			Date Fcongelamiento = new Date();
			for(erEncabezado encabezado: encabezados) {
				encabezado.setE_fechadescongelamiento(Fdescongelamiento);
				Fcongelamiento = encabezado.getE_fechacongelamiento();
				ErEncabezadoService.save(encabezado);
			}
			
			List<er_HojaTrabajo> controles = new ArrayList<er_HojaTrabajo>();
			if(todos) {
				controles = Er_HojaTrabajoService.getHojaTrabajoByProyectoYEstado(de,4);
			}else {
				controles = Er_HojaTrabajoService.getHojaByEncabeIdListYEstado(encabezados_id,4);
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
				control.setEr_estado_id(1);
				Date d1 = Fcongelamiento;
				Date d2 = Fdescongelamiento;
				LocalDateTime date1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime date2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				
				Integer Gap = (int) (long) Duration.between(date1, date2).toDays();
				
				Date d3 = control.getHt_fechaplaneadacierre();
				LocalDateTime newFechaPlanCierre = d3.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				newFechaPlanCierre = newFechaPlanCierre.plusDays(Gap);
				control.setGap(null);
				d3 = Date.from(newFechaPlanCierre.atZone(ZoneId.systemDefault()).toInstant());
				control.setHt_fechaplaneadacierre(d3);
				
				Er_HojaTrabajoService.save(control);
				contador++;
			}
			
				redirectAttributes.addFlashAttribute("successmessage2", "Congelamiento realizado satisfactoriamente. [ "+contador+" ] Registros afectados");
			
				return "redirect:/congelar";
			}
			
				redirectAttributes.addFlashAttribute("errormessage2", "No cargó ninguna evidencia");
				return "redirect:/congelar";
		
	}
	

}


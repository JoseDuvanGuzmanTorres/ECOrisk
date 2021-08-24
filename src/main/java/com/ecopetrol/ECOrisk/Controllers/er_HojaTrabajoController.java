package com.ecopetrol.ECOrisk.Controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Models.erCambios;
import com.ecopetrol.ECOrisk.Models.er_HojaSeguimiento;
import com.ecopetrol.ECOrisk.Models.er_HojaTrabajo;
import com.ecopetrol.ECOrisk.Services.UserService;
import com.ecopetrol.ECOrisk.Services.erCambiosService;
import com.ecopetrol.ECOrisk.Services.er_HojaSeguimientoService;
import com.ecopetrol.ECOrisk.Services.er_HojaTrabajoService;

@Controller
public class er_HojaTrabajoController {
	@Autowired
	private er_HojaTrabajoService Er_HojaTrabajoService;
	@Autowired
	private UserService userService;
	

	@Autowired
	private erCambiosService ErCambiosService;

	@Autowired
	private er_HojaSeguimientoService Er_HojaSeguimientoService;

	@RequestMapping("hojatrabajo/findById")
	public Optional<er_HojaTrabajo> findById(int Id) {
		return Er_HojaTrabajoService.getById(Id);
	}

	@RequestMapping("hojatrabajo/gap")
	public List<er_HojaTrabajo> findGap() {

		// El servidor lleva 5 Horas por delante
		List<er_HojaTrabajo> Todos = Er_HojaTrabajoService.getHojaTrabajoByNoFechaCierre();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date ahora = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(ahora);
		calendar.add(Calendar.HOUR_OF_DAY, -5);
		ahora = calendar.getTime();
		try {
			ahora = formatter.parse(formatter.format(ahora));
			for (er_HojaTrabajo hoja : Todos) {

				Date horatemp = hoja.getHt_fechaplaneadacierre();
				calendar.setTime(horatemp);
				calendar.add(Calendar.HOUR_OF_DAY, -5);
				horatemp = calendar.getTime();
				horatemp = formatter.parse(formatter.format(horatemp));
				if (ahora.compareTo(horatemp) <= 0) {
					hoja.setEr_oportunidad_id(2);
				} else {
					hoja.setEr_oportunidad_id(1);
				}

			}

			Er_HojaTrabajoService.saveAll(Todos);

		} catch (Exception e) {

		}

		return Todos;
	}

	public static String uploadDirectory = System.getProperty("user.home") + File.separator + "uploads";

	@RequestMapping(value = ("hojatrabajo/update"), headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<Object> addNew(@RequestParam("file2") MultipartFile[] file,@RequestParam String ht_id,@RequestParam (required = false)String fecha_nueva,
			@RequestParam (required = false)  String  responsable_nuevo,String comentario, Principal principal) {
		
		String username = principal.getName();
		Users users = userService.loadUserByUsername(username);
		er_HojaTrabajo hoja = Er_HojaTrabajoService.getByHt_id(Integer.parseInt(ht_id));
		
		Date TempDate = hoja.getHt_fechaplaneadacierre();
		Integer TempRespon = hoja.getHt_responsableimplementacion();
		erCambios nuevoCambio = new erCambios();
		
		Date fec_nueva = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// boolean fecha = false;
		
		if (fecha_nueva != null && fecha_nueva != "") {
			try {

				fec_nueva = formatter.parse(fecha_nueva);

			} catch (ParseException e) {
				return new ResponseEntity<>("Error, formato de fecha incorrecto", HttpStatus.OK);
				// e.printStackTrace();
			}
		//	fecha = true;
			hoja.setHt_fechaplaneadacierre(fec_nueva);
			hoja.setEr_oportunidad_id(2);

		}else {
		//	fecha = false;
			hoja.setHt_fechaplaneadacierre(hoja.getHt_fechaplaneadacierre());
			
		}
		
	
	//	boolean resp = false;
		Integer respon = null;
		if (responsable_nuevo != null && responsable_nuevo != "") {
			try {

				respon = Integer.parseInt(responsable_nuevo);

			} catch (Exception e) {
				return new ResponseEntity<>("Error, Nuevo responsable no seleccionado", HttpStatus.OK);
				// e.printStackTrace();
			}
	//		resp = true;
			hoja.setHt_responsableimplementacion(respon);
		}else { 
		//	resp = false;
			hoja.setHt_responsableimplementacion(hoja.getHt_responsableimplementacion());
		}
		
		er_HojaSeguimiento seguimiento = new er_HojaSeguimiento();
		String direccion = "";
		String ruta = "";
		if (file.length < 4) {
			int conta = 0;
			for (MultipartFile archi : file) {
				// Path fileNameAndPath = Paths.get(uploadDirectory,
				// archi.getOriginalFilename());
				Path directorio = Paths.get(uploadDirectory + File.separator + ht_id + File.separator);
				String rutaabsoluta = directorio.toFile().getAbsolutePath();
				if (!(archi.isEmpty())) {
					ruta = "/uploads/cambios/" + nuevoCambio.getEr_cambio_id() + "/";
					try {
						direccion = rutaabsoluta + File.separator + archi.getOriginalFilename();
						Path rutacompleta = Paths.get(direccion);
						Files.createDirectories(directorio);
						Files.write(rutacompleta, archi.getBytes());
						if (conta == 0) {
							seguimiento.setHtseg_evidencia1(archi.getOriginalFilename());
						} else if (conta == 1) {
							seguimiento.setHtseg_evidencia2(archi.getOriginalFilename());
						} else {
							seguimiento.setHtseg_evidencia3(archi.getOriginalFilename());
						}

					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e);
					}
				}
				conta++;

			}
			
			nuevoCambio.setEr_olduser(TempRespon);
			nuevoCambio.setEr_newuser(respon);
			nuevoCambio.setEr_olddate(TempDate);
			nuevoCambio.setEr_newdate(fec_nueva);
			
			ErCambiosService.save(nuevoCambio);
			
			Date date = new Date();
			seguimiento.setHtseg_fechaescrita(date);
			seguimiento.setHtseg_comentario(comentario);
			//seguimiento.setHtseg_id(Integer.parseInt(ht_id));
			seguimiento.setEr_cambio_id(nuevoCambio.getEr_cambio_id());
			
			seguimiento.setHtseg_ruta(ruta);
			seguimiento.setUser_id(users.getId());
			seguimiento.addHojaTrabajo(hoja);

			Er_HojaSeguimientoService.save(seguimiento);
			
			
		}

		return new ResponseEntity<>("Cambio realizado correctamente", HttpStatus.OK);
	}

	@Scheduled(cron = "0 1 5 */1 * *")
	void CronJobUpdateOportunidad2() {
		// El servidor lleva 5 Horas por delante
		List<er_HojaTrabajo> Todos = Er_HojaTrabajoService.getHojaTrabajoByNoFechaCierre();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date ahora = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(ahora);
		calendar.add(Calendar.HOUR_OF_DAY, -5);
		ahora = calendar.getTime();
		try {
			ahora = formatter.parse(formatter.format(ahora));
			for (er_HojaTrabajo hoja : Todos) {

				Date horatemp = hoja.getHt_fechaplaneadacierre();
				calendar.setTime(horatemp);
				calendar.add(Calendar.HOUR_OF_DAY, -5);
				horatemp = calendar.getTime();
				horatemp = formatter.parse(formatter.format(horatemp));
				if (ahora.compareTo(horatemp) <= 0) {
					hoja.setEr_oportunidad_id(2);
				} else {
					hoja.setEr_oportunidad_id(1);
				}

			}

			Er_HojaTrabajoService.saveAll(Todos);

		} catch (Exception e) {

		}
	}

}

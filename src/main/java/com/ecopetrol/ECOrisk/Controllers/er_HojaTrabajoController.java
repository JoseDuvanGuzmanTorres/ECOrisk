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
import com.ecopetrol.ECOrisk.Models.er_HojaSeguimiento;
import com.ecopetrol.ECOrisk.Models.er_HojaTrabajo;
import com.ecopetrol.ECOrisk.Services.UserService;
import com.ecopetrol.ECOrisk.Services.er_HojaSeguimientoService;
import com.ecopetrol.ECOrisk.Services.er_HojaTrabajoService;

@Controller
public class er_HojaTrabajoController {
	@Autowired
	private er_HojaTrabajoService Er_HojaTrabajoService;
	@Autowired
	private UserService userService;

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
	public ResponseEntity<Object> addNew(@RequestParam("file") MultipartFile[] file, String ht_id, String fecha_nueva,
			String responsable_nuevo, Principal principal) {
		String username = principal.getName();
		Users users = userService.loadUserByUsername(username);
		er_HojaTrabajo hoja = Er_HojaTrabajoService.getByHt_id(Integer.parseInt(ht_id));
		Date TempDate = hoja.getHt_fechaplaneadacierre();
		Integer TempRespon = hoja.getHt_responsableimplementacion();

		Date fec_nueva = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		boolean fecha = false;
		if (fecha_nueva != null && fecha_nueva != "") {
			try {

				fec_nueva = formatter.parse(fecha_nueva);

			} catch (ParseException e) {
				return new ResponseEntity<>("Error, formato de fecha incorrecto", HttpStatus.OK);
				// e.printStackTrace();
			}
			fecha = true;
			hoja.setHt_fechaplaneadacierre(fec_nueva);

		}
		boolean resp = false;
		Integer respon = null;
		if (responsable_nuevo != null && responsable_nuevo != "") {
			try {

				respon = Integer.parseInt(responsable_nuevo);

			} catch (Exception e) {
				return new ResponseEntity<>("Error, Nuevo responsable no seleccionado", HttpStatus.OK);
				// e.printStackTrace();
			}
			resp = true;
			hoja.setHt_responsableimplementacion(respon);
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
					ruta = "/uploads/" + ht_id + "/";
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
					}
				}
				conta++;

			}
			Date date = new Date();
			seguimiento.setHtseg_fechaescrita(date);
			String comentario = "";
			if (resp) {
				comentario = comentario + "\n Se realizó un cambio de responsable de "
						+ userService.findById(TempRespon).get().getFullname() + " a "
						+ userService.findById(respon).get().getFullname();
			}
			if (fecha) {
				comentario = comentario + "\n Se realizó un cambio de fecha planeada de cierre, de "
						+ TempDate.toString() + " a " + fec_nueva;
			}
			seguimiento.setHtseg_comentario(comentario);
			// seguimiento.setHt_id(Integer.parseInt(ht_id));
			seguimiento.setHtseg_ruta(ruta);
			seguimiento.setUser_id(users.getId());

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

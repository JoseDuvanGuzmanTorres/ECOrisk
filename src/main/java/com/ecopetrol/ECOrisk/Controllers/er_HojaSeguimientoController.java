package com.ecopetrol.ECOrisk.Controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecopetrol.ECOrisk.Models.er_HojaSeguimiento;
import com.ecopetrol.ECOrisk.Models.er_HojaTrabajo;
import com.ecopetrol.ECOrisk.Projections.er_HojaSeguimientoProjection;
import com.ecopetrol.ECOrisk.Services.er_HojaSeguimientoService;
import com.ecopetrol.ECOrisk.Services.er_HojaTrabajoService;

@Controller
public class er_HojaSeguimientoController {
	@Autowired
	private er_HojaSeguimientoService Er_HojaSeguimientoService;

	@Autowired
	private er_HojaTrabajoService Er_HojaTrabajoService;

	@RequestMapping("hojacomentarios/findByTrabajoId")
	@ResponseBody
	public List<er_HojaSeguimientoProjection> findByTrabajoId(int hojatrabajo_id) {
		return Er_HojaSeguimientoService.getSeguimientoProjectionByTrabajoId(hojatrabajo_id);
	}

	// public static String uploadDirectory =
	// System.getProperty("user.home")+File.separator+"uploads";

	public static String uploadDirectory = "/home/uploads" + File.separator + "seguimiento";

	@RequestMapping(value = ("hojacomentarios/addNew"), headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<Object> addNew(@RequestParam("file") MultipartFile[] file, @RequestParam String au_user,
			@RequestParam String comentario, @RequestParam String open_hojatrabajo, @RequestParam String cambioestado,
			RedirectAttributes attributes) {
		er_HojaSeguimiento seguimiento = new er_HojaSeguimiento();
		String direccion = "";
		String ruta = "";

		if (file.length < 4) {
			int conta = 0;
			for (MultipartFile archi : file) {
				// Path fileNameAndPath = Paths.get(uploadDirectory,
				// archi.getOriginalFilename());
				Path directorio = Paths.get(uploadDirectory + File.separator + open_hojatrabajo + File.separator);
				String rutaabsoluta = directorio.toFile().getAbsolutePath();
				if (!(archi.isEmpty())) {
					ruta = "/uploads/seguimiento/" + open_hojatrabajo + "/";
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
			Date ahora = new Date();
			seguimiento.setHtseg_fechaescrita(ahora);
			seguimiento.setHtseg_comentario(comentario);
			seguimiento.setHtseg_ruta(ruta);
			seguimiento.setUser_id(Integer.parseInt(au_user));
			er_HojaTrabajo hoja = Er_HojaTrabajoService.getByHt_id(Integer.parseInt(open_hojatrabajo));
			seguimiento.addHojaTrabajo(hoja);
			Er_HojaSeguimientoService.save(seguimiento);
			if (Integer.parseInt(cambioestado) == 2) {
				hoja.addSeguimiento(seguimiento);
				hoja.setEr_estado_id(2);
				hoja.setHt_fechacierre(ahora);
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(ahora);
				calendar.add(Calendar.HOUR_OF_DAY, -5);
				ahora = calendar.getTime();
				try {
					ahora = formatter.parse(formatter.format(ahora));

					Date horatemp = hoja.getHt_fechaplaneadacierre();
					calendar.setTime(horatemp);
					calendar.add(Calendar.HOUR_OF_DAY, -5);
					horatemp = calendar.getTime();
					horatemp = formatter.parse(formatter.format(horatemp));
					System.out.println("horatemp : " +horatemp);
					System.out.println("ahora : " +ahora);
					if (ahora.compareTo(horatemp) <= 0) {
						hoja.setEr_oportunidad_id(4);
						System.out.println("Cerrado a tiempo");
					} else {
						hoja.setEr_oportunidad_id(3);
						System.out.println("Cerrado vencido");
					}
					Date d1 = hoja.getHt_fechaplaneadacierre();
					Date d2 = ahora;
					LocalDateTime date1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
					LocalDateTime date2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
					
					Integer Gap = (int) (long) Duration.between(date1, date2).toDays();

					hoja.setGap(Gap);
				} catch (Exception e) {

				}
			}

			Er_HojaTrabajoService.save(hoja);

		}

		return new ResponseEntity<>("Seguimiento agregado correctamente", HttpStatus.OK);
	}
}

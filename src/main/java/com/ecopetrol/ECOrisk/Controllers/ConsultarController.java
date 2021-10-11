package com.ecopetrol.ECOrisk.Controllers;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Models.erDepartamentos;
import com.ecopetrol.ECOrisk.Models.erEtapas;
import com.ecopetrol.ECOrisk.Models.erGerencias;
import com.ecopetrol.ECOrisk.Models.erProceso;
import com.ecopetrol.ECOrisk.Models.erRegionales;
import com.ecopetrol.ECOrisk.Models.erTipoEstudio;
import com.ecopetrol.ECOrisk.Projections.erEncabezadoProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoLeccionesAProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoREmergenteProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoRMaterializadoProjection;
import com.ecopetrol.ECOrisk.Services.UserService;
import com.ecopetrol.ECOrisk.Services.erDepartamentosService;
import com.ecopetrol.ECOrisk.Services.erEtapasService;
import com.ecopetrol.ECOrisk.Services.erGerenciasService;
import com.ecopetrol.ECOrisk.Services.erProcesoService;
import com.ecopetrol.ECOrisk.Services.erRegionalesService;
import com.ecopetrol.ECOrisk.Services.erTipoEstudioService;
import com.ecopetrol.ECOrisk.Services.erEncabezadoService;
import com.ecopetrol.ECOrisk.Services.er_HojaTrabajoService;

@Controller
public class ConsultarController {

	@Autowired
	private erEncabezadoService ErEncabezadoService;
	@Autowired
	private erGerenciasService ErGerenciasService;
	@Autowired
	private erDepartamentosService ErDepartamentosService;
	@Autowired
	private erRegionalesService ErRegionalesService;
	@Autowired
	private erEtapasService ErEtapasService;
	@Autowired
	private er_HojaTrabajoService Er_HojaTrabajoService;
	@Autowired
	private erTipoEstudioService ErTipoEstudioService;
	@Autowired
	private erProcesoService ErProcesoService;
	@Autowired
	private UserService userService;

	// encabezadosList = ErEncabezadoService.getProjectionByUser(null, null, null,
	// null, null, null, null, null, null, null, null, null, Finicial,
	// Ffinal,users.getId());

	@GetMapping("projection/encabezado")
	@ResponseBody
	public List<erEncabezadoProjection> findByTrabajoId(@RequestParam(required = false) Integer user_id,
			@RequestParam(required = false) String proyecto) {

		String tipoestudio = null;
		String proceso = null;
		if (proyecto == null) {
			proyecto = "";
		}
		String codigo = "";
		String nombre = "";
		String objetivo = "";
		String dueno = "";
		String etapa = null;
		String regional = null;
		String gerencia = null;
		String departamento = null;
		String lider = "";
		String instalacion = "";
		String fechainicial = "1970-01-01";
		String fechafinal = "2070-12-31";

		Date Finicial = new Date();
		Date Ffinal = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Finicial = formatter.parse(fechainicial);
			Ffinal = formatter.parse(fechafinal);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<erEncabezadoProjection> encabezadosList = ErEncabezadoService.getProjectionByUser(tipoestudio, proceso,
				proyecto, codigo, nombre, objetivo, etapa, regional, gerencia, dueno, departamento, lider, instalacion,
				Finicial, Ffinal, user_id);

		return encabezadosList;

	}
	
	@GetMapping("projection/encabezadoResponsableT")
	@ResponseBody
	public List<erEncabezadoProjection> FindByResponsableId(@RequestParam Integer responsable) {
		
		String	tipoestudio = null;
		String proceso = null;
		String proyecto = "";
		String codigo = "";
		String nombre = "";
		String objetivo = "";
		String dueno = ""; 
		String responsablet = userService.getByUserId(responsable).getFullname(); ; 
		String etapa = null;
		String regional = null;
		String gerencia = null;
		String departamento = null;
		String lider = ""; 
		String instalacion  = "";
		String fechainicial = "1970-01-01";
		String fechafinal = "2070-12-31";
		Integer user_id = null;
		Date Finicial = new Date();
		Date Ffinal = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Finicial = formatter.parse(fechainicial);
			Ffinal = formatter.parse(fechafinal);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<erEncabezadoProjection> encabezadosList = ErEncabezadoService.getProjectionByUserResponsanleT(tipoestudio, proceso , proyecto, codigo, nombre, objetivo, etapa , regional, gerencia, dueno,responsablet , departamento, lider,instalacion , Finicial, Ffinal, user_id);
		return encabezadosList;
		 
	}
	
	
	
	
	@GetMapping("projection/encabezadoDue")
	@ResponseBody
	public List<erEncabezadoProjection> findByDueId(@RequestParam Integer dueno) {

		String tipoestudio = null;
		String proceso = null;
		String proyecto = "";
		String codigo = "";
		String nombre = "";
		String objetivo = "";
		String duenio = userService.getByUserId(dueno).getFullname();
		String etapa = null;
		String regional = null;
		String gerencia = null;
		String departamento = null;
		String lider = "";
		String instalacion = "";
		String fechainicial = "1970-01-01";
		String fechafinal = "2070-12-31";
		Integer user_id = null;

		Date Finicial = new Date();
		Date Ffinal = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Finicial = formatter.parse(fechainicial);
			Ffinal = formatter.parse(fechafinal);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<erEncabezadoProjection> encabezadosList = ErEncabezadoService.getProjectionByUser(tipoestudio, proceso,
				proyecto, codigo, nombre, objetivo, etapa, regional, gerencia, duenio, departamento, lider, instalacion,
				Finicial, Ffinal, user_id);

		return encabezadosList;

	}

	@GetMapping("/consultar")
	public String loadWhatIf(Model model, @RequestParam(required = false) String tipoestudio,
			@RequestParam(required = false) String proceso, @RequestParam(required = false) String proyecto,
			@RequestParam(required = false) String codigo, @RequestParam(required = false) String nombre,
			@RequestParam(required = false) String objetivo, @RequestParam(required = false) String dueno,
			@RequestParam(required = false) String etapa, @RequestParam(required = false) String regional,
			@RequestParam(required = false) String gerencia, @RequestParam(required = false) String departamento,
			@RequestParam(required = false) String lider, @RequestParam(required = false) String instalacion,
			@RequestParam(required = false) String fechainicial, @RequestParam(required = false) String fechafinal,
			Principal principal) {

		if (tipoestudio != null && tipoestudio.equals("")) {
			tipoestudio = null;
		}

		if (proceso != null && proceso.equals("")) {
			proceso = null;
		}

		if (proyecto == null) {
			proyecto = "";
		}

		if (codigo == null) {
			codigo = "";
		}

		if (nombre == null) {
			nombre = "";
		}

		if (objetivo == null) {
			objetivo = "";
		}

		if (etapa != null && etapa.equals("")) {
			etapa = null;
		}

		if (regional != null && regional.equals("")) {
			regional = null;
		}

		if (gerencia != null && gerencia.equals("")) {
			gerencia = null;
		}

		if (dueno == null) {
			dueno = "";
		}

		if (departamento != null && departamento.equals("")) {
			departamento = null;
		}

		if (lider == null) {
			lider = "";
		}

		if (instalacion == null) {
			instalacion = "";
		}

		if ((fechainicial != null && fechainicial.equals("")) || fechainicial == null) {
			fechainicial = "1970-01-01";
		}

		if ((fechafinal != null && fechafinal.equals("")) || fechafinal == null) {
			fechafinal = "2070-12-31";
		}

		Date Finicial = new Date();
		Date Ffinal = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Finicial = formatter.parse(fechainicial);
			Ffinal = formatter.parse(fechafinal);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Integer Id;
		String username = principal.getName();
		Users users = userService.loadUserByUsername(username);
		if (users.getRoles_id() == 1 || users.getRoles_id() == 2 || users.getRoles_id() == 3 || users.getRoles_id() == 6
				|| users.getRoles_id() == 7) {
			Id = null;
		} else {
			Id = users.getId();
		}

		List<erEncabezadoProjection> encabezadosList = ErEncabezadoService.getProjectionByUser(tipoestudio, proceso,
				proyecto, codigo, nombre, objetivo, etapa, regional, gerencia, dueno, departamento, lider, instalacion,
				Finicial, Ffinal, Id);
		model.addAttribute("encabezadosList", encabezadosList);

		List<Integer> id_encabeList = new ArrayList<Integer>();
		for (erEncabezadoProjection encabe : encabezadosList) {
			id_encabeList.add(encabe.getId());
		}

		if (users.getRoles_id() == 1) {
			Id = null;
		}
		List<er_HojaTrabajoProjection> MasterhojatrabajoList2 = Er_HojaTrabajoService
				.getAllProjectionByIdEncabeAndUserId(id_encabeList, tipoestudio, Id);
		model.addAttribute("MasterhojatrabajoList", MasterhojatrabajoList2);

		List<er_HojaTrabajoLeccionesAProjection> LeccioneshojatrabajoList = Er_HojaTrabajoService
				.getAllHojaTrabajoLAProjectionByEncabezado(id_encabeList, null);
		model.addAttribute("LeccioneshojatrabajoList", LeccioneshojatrabajoList);

		List<er_HojaTrabajoREmergenteProjection> REhojatrabajoList = Er_HojaTrabajoService
				.getAllHojaTrabajoREProjectionByEncabezado(id_encabeList, Id);
		model.addAttribute("REhojatrabajoList", REhojatrabajoList);

		List<er_HojaTrabajoRMaterializadoProjection> RMhojatrabajoList = Er_HojaTrabajoService
				.getAllHojaTrabajoRMProjectionByEncabezado(id_encabeList, Id);
		model.addAttribute("RMhojatrabajoList", RMhojatrabajoList);

		List<erGerencias> gerenciasList = ErGerenciasService.getGerenciasAsc();
		List<erDepartamentos> departamentosList = ErDepartamentosService.getDepartamentosAsc();
		List<erRegionales> regionalesList = ErRegionalesService.getRegionalesAsc();
		List<erEtapas> etapasList = ErEtapasService.getEtapasAsc();
		List<erTipoEstudio> tiposestudioList = ErTipoEstudioService.getTiposDeEstudioAsc();
		List<erProceso> procesoList = ErProcesoService.getProcesosAsc();
		model.addAttribute("gerenciasList", gerenciasList);
		model.addAttribute("departamentosList", departamentosList);
		model.addAttribute("regionalesList", regionalesList);
		model.addAttribute("etapasList", etapasList);
		model.addAttribute("tiposestudioList", tiposestudioList);
		model.addAttribute("procesoList", procesoList);

		return "consultar";
	}

	@GetMapping("/load/cambios")
	public String loadCambios(Model model) {
		List<er_HojaTrabajoProjection> hojatrabajoList = Er_HojaTrabajoService.getAllProjection();

		model.addAttribute("hojatrabajoList", hojatrabajoList);

		List<Users> usuariosList = userService.findAll();
		model.addAttribute("usuariosList", usuariosList);

		List<Users> usuariosDuenosList = userService.getUsersByDueno();
		model.addAttribute("usuariosDuenosList", usuariosDuenosList);

		List<Users> usuariosDuenosAllList = userService.getUsersByAllDueno();
		model.addAttribute("usuariosDuenosAllList", usuariosDuenosAllList);

		return "cambios";
	}

}

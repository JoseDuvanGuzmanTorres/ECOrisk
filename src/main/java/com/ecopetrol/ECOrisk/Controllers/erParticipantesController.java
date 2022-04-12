package com.ecopetrol.ECOrisk.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecopetrol.ECOrisk.Projections.erParticipantesProjection;
import com.ecopetrol.ECOrisk.Services.erParticipantesService;


/**
 * 
 * controlador de los participantes de un proyecto
 * @author Manuel Eduardo Patarroyo Santos
 * 
 * 
 */

@Controller
public class erParticipantesController {

	@Autowired
	erParticipantesService ErParticipantesService;
	//se define el mapeo de los participantes (esta no se dice que es una url porque no tiene una pagina html dentro de ECOrisk)
	@RequestMapping("participantes/findByEncabezadoId")
	@ResponseBody
	//se crea una lista de participantes y se enlaza con el encabezado para que queden relacionadas las dos entidades
	public List<erParticipantesProjection> findByEncabezadoId(int encabezado_id) {
		return ErParticipantesService.getPartiByEncabeIdProjection(encabezado_id);
	}
	
}

package com.ecopetrol.ECOrisk.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecopetrol.ECOrisk.Projections.erParticipantesProjection;
import com.ecopetrol.ECOrisk.Services.erParticipantesService;

@Controller
public class erParticipantesController {

	@Autowired
	erParticipantesService ErParticipantesService;
	
	@RequestMapping("participantes/findByEncabezadoId")
	@ResponseBody
	public List<erParticipantesProjection> findByEncabezadoId(int encabezado_id) {
		return ErParticipantesService.getPartiByEncabeIdProjection(encabezado_id);
	}
	
}

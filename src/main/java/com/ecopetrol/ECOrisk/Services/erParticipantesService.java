package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecopetrol.ECOrisk.Models.erParticipantes;
import com.ecopetrol.ECOrisk.Projections.erParticipantesProjection;
import com.ecopetrol.ECOrisk.Repositories.erParticipantesRepository;

@Service
public class erParticipantesService {

	@Autowired
	erParticipantesRepository ErParticipantesRepository;
	
	
	public List<erParticipantes> getRoles(){
		return ErParticipantesRepository.findAll();
	}

	//Update
	public void save(erParticipantes participante) {
		ErParticipantesRepository.save(participante);
	}
	
	public Optional<erParticipantes> getById(int id) {
		return ErParticipantesRepository.findById(id);
	}
	
	//Projection
	public List<erParticipantesProjection> getPartiByEncabeIdProjection(Integer encabezado_id) {
		return ErParticipantesRepository.findPartiByEncabeIdProjection(encabezado_id);
	}
}

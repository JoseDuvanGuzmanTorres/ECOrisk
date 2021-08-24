package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erEmailsEnviados;
import com.ecopetrol.ECOrisk.Projections.erEmailsEnviadosProjection;
import com.ecopetrol.ECOrisk.Repositories.erEmailsEnviadosRepository;

@Service
public class erEmailsEnviadosService {
	
	@Autowired
	erEmailsEnviadosRepository ErEmailsEnviadosRepository;
	
	
	public List<erEmailsEnviados> getEmails(){
		return ErEmailsEnviadosRepository.findAll();
	}

	//Update
	public void save(erEmailsEnviados email) {
		ErEmailsEnviadosRepository.save(email);
	}
	
	//Update
	public void saveAll(List<erEmailsEnviados> emails) {
		ErEmailsEnviadosRepository.saveAll(emails);
	}
	
	public Optional<erEmailsEnviados> getById(int id) {
		return ErEmailsEnviadosRepository.findById(id);
	}
	
	//Projection
	public List<erEmailsEnviadosProjection> getEmailsProjection(){
		return ErEmailsEnviadosRepository.findByAllEmailsProjection();
	}
}

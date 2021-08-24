package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erPortafolio;
import com.ecopetrol.ECOrisk.Repositories.erPortafolioRepository;

@Service
public class erPortafolioService {
	
	@Autowired
	private erPortafolioRepository ErPortafolioRepository;

	
	//Get All Users
	public List<erPortafolio> findAll(){
		return ErPortafolioRepository.findAll();
	}	
	
	//Get Users By Id
	public Optional<erPortafolio> findById(int id) {
		return ErPortafolioRepository.findById(id);
	}	
	
	//Delete Users
	public void delete(int id) {
		ErPortafolioRepository.deleteById(id);
	}
	
	//Delete all Users in list
	public void deleteAllByList(List<erPortafolio> portafolios) {
		ErPortafolioRepository.deleteAll(portafolios);
	}
	
	//Update Users
	public void save(erPortafolio portafolios) {
		ErPortafolioRepository.save(portafolios);
	}
	
	
	//Get by Id
	public erPortafolio loadPortafolioById(Integer id) {
		return ErPortafolioRepository.findByPortafolioId(id);
	}
	
	//Get by Id
	public List<erPortafolio> loadPortafolioByProcesoId(Integer proceso) {
		return ErPortafolioRepository.findByPortafolioProcesoId(proceso);
		
	}
	

}

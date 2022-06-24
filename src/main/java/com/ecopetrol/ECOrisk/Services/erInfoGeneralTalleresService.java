package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erInfoGeneralTalleres;
import com.ecopetrol.ECOrisk.Repositories.erInfoGeneralTalleresRepository;

@Service
public class erInfoGeneralTalleresService {
	
	@Autowired
	private erInfoGeneralTalleresRepository erInfoGeneralTalleresRepository;

	
	//Get All Users
	public List<erInfoGeneralTalleres> encontrartodo(){
		return erInfoGeneralTalleresRepository.findAll();
	}	
	
	//Get Users By Id
	public Optional<erInfoGeneralTalleres> findById(int id) {
		return erInfoGeneralTalleresRepository.findById(id);
	}	
	
	//Delete Users
	public void delete(int id) {
		erInfoGeneralTalleresRepository.deleteById(id);
	}
	
	//Delete all Users in list
	public void deleteAllByList(List<erInfoGeneralTalleres> infotalleres) {
		erInfoGeneralTalleresRepository.deleteAll(infotalleres);
	}
	
	//Update Users
	public void save(erInfoGeneralTalleres infotalleres) {
		erInfoGeneralTalleresRepository.save(infotalleres);
	}
	
	
	//Get by Id
	public erInfoGeneralTalleres loadPortafolioById(Integer id) {
		return erInfoGeneralTalleresRepository.findByinfoGeneralTalleresId(id);
	}
	

	

}

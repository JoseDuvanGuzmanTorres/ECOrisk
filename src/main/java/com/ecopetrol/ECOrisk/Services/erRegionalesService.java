package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.erRegionales;
import com.ecopetrol.ECOrisk.Repositories.erRegionalesRepository;

@Service
public class erRegionalesService {

	@Autowired
	private erRegionalesRepository ErRegionalesRepository;
	
	public List<erRegionales> getRegionales(){
		return ErRegionalesRepository.findAll();
	}
	
	//Update Country
	public void save(erRegionales regional) {
		ErRegionalesRepository.save(regional);
	}
	
	public Optional<erRegionales> getById(int id) {
		return ErRegionalesRepository.findById(id);
	}
	
	public erRegionales getByRegional(String regional){
		return ErRegionalesRepository.findByRegional(regional);
	}
	
	//Custom Filter
	public List<erRegionales> getRegionalesAsc(){
		return ErRegionalesRepository.findByAllAsc();
	}
}

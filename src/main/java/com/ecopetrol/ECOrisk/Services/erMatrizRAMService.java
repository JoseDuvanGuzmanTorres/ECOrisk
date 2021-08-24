package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecopetrol.ECOrisk.Models.erMatrizRAM;
import com.ecopetrol.ECOrisk.Repositories.erMatrizRAMRepository;

@Service
public class erMatrizRAMService {

	@Autowired
	private erMatrizRAMRepository ErMatrizRAMRepository;
	
	public List<erMatrizRAM> getMatrices(){
		return ErMatrizRAMRepository.findAll();
	}
	
	//Update
	public void save(erMatrizRAM matriz) {
		ErMatrizRAMRepository.save(matriz);
	}
	
	public Optional<erMatrizRAM> getById(int id) {
		return ErMatrizRAMRepository.findById(id);
	}
	
	public erMatrizRAM getByMatriz(String matriz){
		return ErMatrizRAMRepository.findByMatriz(matriz);
		
	}
	
	//Custom Filter
	public List<erMatrizRAM> getMatricesAsc(){
		return ErMatrizRAMRepository.findByAllAsc();
	}
	
}

package com.ecopetrol.ECOrisk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Projections.UsersProjection;
import com.ecopetrol.ECOrisk.Repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	//Get All Users
	public List<Users> findAll(){
		return userRepository.findAll();
	}	
	
	//Get Users By Id
	public Optional<Users> findById(int id) {
		return userRepository.findById(id);
	}	
	
	//Delete Users
	public void delete(int id) {
		userRepository.deleteById(id);
	}
	
	//Delete all Users in list
	public void deleteAllByList(List<Users> usuarios) {
		userRepository.deleteAll(usuarios);
	}
	
	//Update Users
	public void save(Users users) {
		userRepository.save(users);
	}
	
	public void newUsersSave(Users users) {
		users.setPassword(encoder.encode("12345"));
		userRepository.save(users);
		
	}
	
	
	//Get by Id
	public Users loadUserById(Integer id) {
		return userRepository.findByUsersId(id);
	}
		
	
	//Get by Username
	public Users loadUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	//Get by Fullname
	public List<Users> loadUserByFullname(String fullname) {
		return userRepository.findByFullname(fullname);
	}	
	
	//Get by ID
	public Users getByUserId(Integer id) {
		return userRepository.findByUsersId(id);
	}
	
	//Get users con controles abiertos
	public List<Users> getUsersByControlesAbiertos(){
		return userRepository.findUsersByControlesAbiertos();
	}
	
	//Get users duenos
	public List<Users> getUsersByDueno(){
		return userRepository.findUsersByDueno();
	}
	
	public List<Users> getUsersByAllDueno(){
		return userRepository.findUsersByAllDueno();
	}
	
	//Get users 
	public List<Users> getUsersBySemiAdmin(){
		return userRepository.findUsersBySemiAdmin();
	}
	
	//Projection
	public List<UsersProjection> getUserProjection(){
		return userRepository.findUsersProjection();
	}
	
	

}

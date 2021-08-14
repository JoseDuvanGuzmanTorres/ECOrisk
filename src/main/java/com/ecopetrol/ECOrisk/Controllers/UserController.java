package com.ecopetrol.ECOrisk.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecopetrol.ECOrisk.Models.Roles;
import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Services.RolesService;
import com.ecopetrol.ECOrisk.Services.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	//Modified method to Add a new user Users
	/*@PostMapping(value="users/addNew")
	public RedirectView addNew(Users user, RedirectAttributes redir) {
		userService.save(user);	
		RedirectView  redirectView= new RedirectView("/login",true);
	        redir.addFlashAttribute("message",
	    		"Se ha registrado correctamente, ya puede ingresar");
	    return redirectView;				
	}
	*/public UserController() {
		// TODO Auto-generated constructor stub
	}
	//Get All Users
	@GetMapping("users")
	public String findAll(Model model){	
		List<Users> usuarios = userService.findAll();
		List<Roles> roles = rolesService.getRoles();
		
		model.addAttribute("roles", roles);
		model.addAttribute("usuarios", usuarios);
		return "user";
	}	
	
	@RequestMapping("users/findById") 
	@ResponseBody
	public Optional<Users> findById(Integer id)
	{
		return userService.findById(id);
	}
	
	//Add Users
	
	@PostMapping(value="users/addNew")
	public String addNew(Users users) {
		userService.save(users);
		return "redirect:/users";
	}
	
	@PostMapping(value="users/addNew2")
	public String addNew2(Users users) {
		userService.newUsersSave(users);
		return "redirect:/users";
	}
	
	
	@RequestMapping(value="users/update", method = {RequestMethod.PUT, RequestMethod.GET})
	public String update(Users users) {
		String OldUser = userService.loadUserById(users.getId()).getPassword();
		String New = users.getPassword();
		if(!OldUser.equals(New)) {
			users.setPassword(encoder.encode(users.getPassword())); 
		}
		userService.save(users);
		return "redirect:/users";
	}
	
	@RequestMapping(value="users/delete", method = {RequestMethod.DELETE, RequestMethod.GET})	
	public String delete(Integer id) {
		userService.delete(id);
		return "redirect:/users";
	}


}

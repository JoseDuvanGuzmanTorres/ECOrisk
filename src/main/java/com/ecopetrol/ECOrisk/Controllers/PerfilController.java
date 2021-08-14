package com.ecopetrol.ECOrisk.Controllers;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecopetrol.ECOrisk.Models.Roles;
import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Services.RolesService;
import com.ecopetrol.ECOrisk.Services.UserService;

@Controller
public class PerfilController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping("/perfil")
	public String perfil(Model model, Principal principal) {
		
		String username = principal.getName();
		Users users = userService.loadUserByUsername(username);
		Optional<Roles> rol = rolesService.getById(users.getRoles_id());
		String userRol = rol.get().getRol();
		model.addAttribute("user", users);
		model.addAttribute("userRol", userRol);
		return "profile";
	}
	
	@RequestMapping(value="profile/update", method = {RequestMethod.POST})
	public String update(@RequestParam(required = false) String actual,@RequestParam(required = false) String nueva, Principal principal, RedirectAttributes redirectAttributes) {
		String username = principal.getName();
		Users users = userService.loadUserByUsername(username);
		
		if(encoder.matches(actual, users.getPassword())){
			if(nueva != null) {
				users.setPassword(encoder.encode(nueva));
				userService.save(users);
				redirectAttributes.addFlashAttribute("successmessage", "Cambios guardados correctamente");
			}
			else {
				redirectAttributes.addFlashAttribute("errormessage", "Error, la contraseña actual no puede estar vacía");
			}
		}else {
			redirectAttributes.addFlashAttribute("errormessage", "Error, la contraseña actual no es correcta");
		}
		
		
		
		return "redirect:/perfil";
	}
	
}

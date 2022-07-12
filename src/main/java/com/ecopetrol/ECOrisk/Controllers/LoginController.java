package com.ecopetrol.ECOrisk.Controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * Controlador para la pagina de login
 * 
 * @author Manuel Eduardo Patarroyo Santos
 *
 */


@Controller
public class LoginController {
	/* Se asigna la url de la pagina en caso de error */
    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
    	//se configura la autenticacion de la pagina
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
        	//obtencion de la autenticacion
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                //errorMessage = ex.getMessage();
            	errorMessage ="Error al iniciar sesión. Revise sus credenciales e intente de nuevo.";
            }
        }
        //mensaje de error si aplica
        model.addAttribute("errorMessage", errorMessage);
        return "login";//se vuelve a cargar la pagina de login
    }
}
package com.ecopetrol.ECOrisk.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Models.erEmailsEnviados;
import com.ecopetrol.ECOrisk.Projections.IndicadoresProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoLeccionesAProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoProjection;
import com.ecopetrol.ECOrisk.Services.EmailService;
import com.ecopetrol.ECOrisk.Services.UserService;
import com.ecopetrol.ECOrisk.Services.erEmailsEnviadosService;
import com.ecopetrol.ECOrisk.Services.erProyectoService;
import com.ecopetrol.ECOrisk.Services.er_HojaTrabajoService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SendingController {

    @Autowired
    private EmailService emailService;
    @Autowired
	private er_HojaTrabajoService Er_HojaTrabajoService;
    @Autowired
    private UserService userService;
    @Autowired
    private erProyectoService ErProyectoService;
    @Autowired
    private erEmailsEnviadosService ErEmailsEnviadosService;

    /* Send plain TEXT mail */
    @RequestMapping(value = "/sendMailText", method = POST)
    public String sendTextMail(
        @RequestParam("recipientName") final String recipientName,
        @RequestParam("recipientEmail") final String recipientEmail,
        final Locale locale)
        throws MessagingException {

        this.emailService.sendTextMail(recipientName, recipientEmail, locale);
        return "redirect:sent.html";

    }
    
    /* Send HTML mail (simple) */
    @RequestMapping(value = "/sendMailSimple", method = POST)
    public String sendSimpleMail(
        @RequestParam("recipientName") final String recipientName,
        @RequestParam("recipientEmail") final String recipientEmail,
        final Locale locale)
        throws MessagingException {

        this.emailService.sendSimpleMail(recipientName, recipientEmail, locale);
        return "redirect:sent.html";

    }

    /* Send HTML mail with attachment. */
    @RequestMapping(value = "/sendMailWithAttachment", method = POST)
    public String sendMailWithAttachment(
        @RequestParam("recipientName") final String recipientName,
        @RequestParam("recipientEmail") final String recipientEmail,
        @RequestParam("attachment") final MultipartFile attachment,
        final Locale locale)
        throws MessagingException, IOException {

        this.emailService.sendMailWithAttachment(
            recipientName, recipientEmail, attachment.getOriginalFilename(),
            attachment.getBytes(), attachment.getContentType(), locale);
        return "redirect:sent.html";

    }

    @RequestMapping(value = "/sendMailDown")
    public String sendMailDown() throws MessagingException, InterruptedException {
    	List<Users> todos = userService.findAll();
    	
    
    	String asunto = "Interrupción en el servicio de ECOrisk";
    	Context ctx = new Context();
    	String dia = "10/09/2021";
    	String horainicio = "7:50 am";
    	String horafin = "08:00 am";
    	ctx.setVariable("dia", dia);
    	ctx.setVariable("horainicio", horainicio);
    	ctx.setVariable("horafin", horafin);
    	
    	List<erEmailsEnviados> enviados = new ArrayList<erEmailsEnviados>();
    	for(Users usuario : todos) {
        	String email = usuario.getUsername();
        	if(esEmail(email)) {
        		String Para = email;
        		
        		emailService.sendMailDown(Para,asunto,ctx);
        		System.out.println("Salió down : "+ email);
        		erEmailsEnviados nuevo = new erEmailsEnviados();
        		Date fecha = new Date();
        		nuevo.setAsunto(asunto);
        		nuevo.setFecha(fecha);
        		nuevo.setUser_id(usuario.getId());
        		enviados.add(nuevo);
        		Thread.sleep(1000);
        		
        	}
    	}
    	
    	ErEmailsEnviadosService.saveAll(enviados);
    	
    	
    	System.out.println("Si funciona");
    	return "redirect:/";
    }
    
    //Pruebas
    
    @RequestMapping(value = "/emailPruebas")
    public String sendMailTest() throws MessagingException, InterruptedException {
    	
    	Context ctx = new Context();
		String Para = "joseduvanguzmantorres@gmail.com";
		String Asunto = "Tiene controles sin cerrar en ECOrisk";
		String nombre = "Jose Duvan Guzman Torres";
		ctx.setVariable("nombre", nombre);
		
		List<IndicadoresProjection> IndicadoresProyectoList = ErProyectoService.getAllIndicadoresProyecto(29);
		ctx.setVariable("IndicadoresProyectoList", IndicadoresProyectoList);
		
		List<er_HojaTrabajoProjection> hojatrabajoList = Er_HojaTrabajoService.getAllProjectionAbiertasByDueno(29);
		ctx.setVariable("hojatrabajoList", hojatrabajoList);
		
		  List<IndicadoresProjection> IndicadoresLA = ErProyectoService.getAllIndicadoresLA(29);
		ctx.setVariable("IndicadoresLA", IndicadoresLA);
		
		 List<er_HojaTrabajoLeccionesAProjection> leccionesAList=  Er_HojaTrabajoService.getAllHojaTrabajoLAProjectionByUserId(29);
		ctx.setVariable("leccionesAList", leccionesAList);
		
		emailService.sendMailIndicadores(Para,Asunto,ctx);
				
		emailService.sendMailIndicadores(Para,Asunto,ctx);
		System.out.println("Salida controles: "+Para);
		Thread.sleep(1000);	
    	return "redirect:/";
    }
    
    //Custom2
    //@RequestMapping(value = "/iniciar2")
    //0 30 10 */1 * 2 martes 0 0 13 */1 * 1 lunes
   
    void sendMailComplex() throws MessagingException, InterruptedException {
    	
    	//Envía emails a los usuarios con rol LiderProcesoGir y Administrador de riesgos
    	List<Users> semiadmins = userService.getUsersBySemiAdmin();
    	List<erEmailsEnviados> enviados = new ArrayList<erEmailsEnviados>();
		/*
		 * for(Users usuario : semiadmins) { String email = usuario.getUsername();
		 * 
		 * if(esEmail(email)) {
		 * 
		 * Context ctx = new Context(); String Para = email; String Asunto =
		 * "Reporte de indicadores de ECOrisk";
		 * 
		 * List<IndicadoresProjection> IndicadoresProyectoList =
		 * ErProyectoService.getAllIndicadoresProyecto(null);
		 * ctx.setVariable("IndicadoresProyectoList", IndicadoresProyectoList);
		 * List<IndicadoresProjection> IndicadoresFuncionarioList =
		 * ErProyectoService.getAllIndicadoresFuncionario(null);
		 * ctx.setVariable("IndicadoresFuncionarioList", IndicadoresFuncionarioList);
		 * 
		 * emailService.sendMailIndicadores(Para,Asunto,ctx);
		 * System.out.println("Salió LiderProcesoGir :"+ email);
		 * 
		 * erEmailsEnviados nuevo = new erEmailsEnviados(); Date fecha = new Date();
		 * nuevo.setAsunto(Asunto); nuevo.setFecha(fecha);
		 * nuevo.setUser_id(usuario.getId()); enviados.add(nuevo); Thread.sleep(1000);
		 * 
		 * } }
		 * 
		 * //Envía emails a todos los dueños de proyecto List<Users> duenos =
		 * userService.getUsersByDueno(); for(Users usuario : duenos) { String email =
		 * usuario.getUsername(); if(esEmail(email)) { Context ctx = new Context();
		 * String Para = email; String Asunto =
		 * "Reporte de proyectos y talleres ECOrisk";
		 * 
		 * List<IndicadoresProjection> IndicadoresProyectoList =
		 * ErProyectoService.getAllIndicadoresProyecto(usuario.getId());
		 * ctx.setVariable("IndicadoresProyectoList", IndicadoresProyectoList);
		 * 
		 * List<er_HojaTrabajoProjection> hojatrabajoList =
		 * Er_HojaTrabajoService.getAllProjectionAbiertasByDueno(usuario.getId());
		 * ctx.setVariable("hojatrabajoList", hojatrabajoList);
		 * 
		 * List<IndicadoresProjection> IndicadoresLA =
		 * ErProyectoService.getAllIndicadoresLA(usuario.getId());
		 * ctx.setVariable("IndicadoresLA", IndicadoresLA);
		 * 
		 * List<er_HojaTrabajoLeccionesAProjection> leccionesAList=
		 * Er_HojaTrabajoService.getAllHojaTrabajoLAProjectionByUserId(usuario.getId());
		 * ctx.setVariable("leccionesAList", leccionesAList);
		 * 
		 * 
		 * emailService.sendMailIndicadoresLideres(Para,Asunto,ctx);
		 * System.out.println("Salió dueño :"+ email); erEmailsEnviados nuevo = new
		 * erEmailsEnviados(); Date fecha = new Date(); nuevo.setAsunto(Asunto);
		 * nuevo.setFecha(fecha); nuevo.setUser_id(usuario.getId());
		 * enviados.add(nuevo); Thread.sleep(1000);
		 * 
		 * } }
		 */
    	
    	
    	//Envía emails a todos aquellos que tengan controles abiertos
    	
        List<Users> usuarios = userService.getUsersByControlesAbiertos();
        for(Users usuario : usuarios) {
        	String email = usuario.getUsername();
        	String nombreu = usuario.getFullname();
        	if(esEmail(email)) {
        		Context ctx = new Context();
        		String Para = email;
        		String Asunto = "Tiene controles sin cerrar en ECOrisk";
        		
        		List<er_HojaTrabajoProjection> hojatrabajoList = Er_HojaTrabajoService.getAllProjectionAbiertasByUserId(usuario.getId());
        		ctx.setVariable("hojatrabajoList", hojatrabajoList);
        		
        		List<er_HojaTrabajoLeccionesAProjection> leccionesAList=  Er_HojaTrabajoService.getAllHojaTrabajoLAProjectionByUserId(usuario.getId());
        		ctx.setVariable("leccionesAList", leccionesAList);
        		
        		ctx.setVariable("nombreu", nombreu);
        		emailService.sendMailControles(Para,Asunto,ctx);
        		System.out.println("Salió controles: "+email);
        		
        		erEmailsEnviados nuevo = new erEmailsEnviados();
        		Date fecha = new Date();
        		nuevo.setAsunto(Asunto);
        		nuevo.setFecha(fecha);
        		nuevo.setUser_id(usuario.getId());
        		enviados.add(nuevo);
        		Thread.sleep(1000);
                
        	}
        	
        }
        
        ErEmailsEnviadosService.saveAll(enviados);
        
    }

	public boolean esEmail(String email) {
    	if(email == null) {
    		email = "";
    	}
    	if(!email.equals("")) {
    		Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
            Matcher mat = pattern.matcher(email);
            if(mat.matches()){
            	return true;
            }
    	}
    	return false;
    }
    
    /* Send editable HTML mail */
    @RequestMapping(value = "/sendEditableMail", method = POST)
    public String sendMailWithInline(
        @RequestParam("recipientName") final String recipientName,
        @RequestParam("recipientEmail") final String recipientEmail,
        @RequestParam("body") final String body,
        final Locale locale)
        throws MessagingException, IOException {

        this.emailService.sendEditableMail(
            recipientName, recipientEmail, body, locale);
        return "redirect:sent.html";

    }

}
package com.ecopetrol.ECOrisk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * Se define la inicialización de ECOrisk, este archivo se ejecuta cada vez que se reinicia ECOrisk 
 * 
 * @author Manuel Eduardo Patarroyo Santos
 *
 */

@SpringBootApplication
public class ECOriskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECOriskApplication.class, args);
	}
	

//inicialización de la configuracion de ECOrisk
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)//activación de las tareas programadas con @cron y/o otras funciones que requieran utilizar el reloj del servidor
class SchedulingConfiguration{
	}
}


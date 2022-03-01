package com.ecopetrol.ECOrisk;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * UploadDirConfig declara el modelo de las paginas de cargue de talleres
 * 
 * @author José Duvan Guzmán Torres
 *
 */

@Configuration
public class UploadDirConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//se define la estructura http para las direcciones de las paginas de cargue de talleres y en donde se guardan
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler(File.separator+"uploads"+File.separator+"**").addResourceLocations("File:/home/uploads/"
);
	}

}

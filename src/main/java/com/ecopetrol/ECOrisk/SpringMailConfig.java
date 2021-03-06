package com.ecopetrol.ECOrisk;

//clases y complementos de java,springboot y thymeleaf que se utilizan

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * SpringMailConfig declara las variables para los correos, el formato de
 * codificacion de caracteres
 * 
 * @author Manuel Eduardo Patarroyo Santos
 *
 */



public @Configuration @PropertySource("classpath:mail/emailconfig.properties") class SpringMailConfig
		implements ApplicationContextAware, EnvironmentAware {
	// se define el formato de codificacion
	public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

	private static final String JAVA_MAIL_FILE = "classpath:mail/javamail.properties";

	private static final String HOST = "mail.server.host";

	private static final String PORT = "mail.server.port";

	private static final String PROTOCOL = "mail.server.protocol";

	private static final String USERNAME = "mail.server.username";

	private static final String PASSWORD = "mail.server.password";

	private ApplicationContext applicationContext;
	private Environment environment;

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.environment = environment;
	}

	/*
	 * SPRING + JAVAMAIL: JavaMailSender instancia configurada por .properties files
	 * para el envio de correos a traves de springboot.
	 * 
	 */

	@Bean
	public JavaMailSender mailSender() throws IOException {

		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// Basic mail sender configuration, based on emailconfig.properties
		mailSender.setHost(this.environment.getProperty(HOST));
		mailSender.setPort(Integer.parseInt(this.environment.getProperty(PORT)));
		mailSender.setProtocol(this.environment.getProperty(PROTOCOL));
		mailSender.setUsername(this.environment.getProperty(USERNAME));
		mailSender.setPassword(this.environment.getProperty(PASSWORD));

		// JavaMail-specific mail sender configuration, based on javamail.properties
		final Properties javaMailProperties = new Properties();
		javaMailProperties.load(this.applicationContext.getResource(JAVA_MAIL_FILE).getInputStream());
		mailSender.setJavaMailProperties(javaMailProperties);

		return mailSender;

	}

	/*
	 * Externalizaci??n/internacionalizaci??n de mensajes para correos electr??nicos.
	 *
	 * NOTA estamos evitando el uso del nombre 'messageSource' para este bean porque
	 * eso har??a que el MessageSource definido en SpringWebConfig (y disponible para
	 * el motor de plantillas del lado web) delegado a este, y por lo tanto, combine
	 * de manera efectiva los mensajes de correo electr??nico en mensajes web y haga
	 * ambos tipos disponible en el lado web, lo que podr??a traer colisiones no
	 * deseadas.
	 *
	 * TENGA EN CUENTA tambi??n que dado que queremos que esta fuente de mensaje
	 * espec??fica sea utilizada por nuestro SpringTemplateEngines para correos
	 * electr??nicos (y no por el web), lo configuraremos expl??citamente en cada uno
	 * de los objetos TemplateEngine con 'establecerTemplateEngineMessageSource'
	 */
	
	@Bean
	public ResourceBundleMessageSource emailMessageSource() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("mail/MailMessages");
		return messageSource;
	}

	/**
	 * TemplateEngine se crean las variables para todos los tipos de correos y se
	 * enlasan a una funcion, para luego definir su configuracion
	 * 
	 */
	@Bean
	@Qualifier("email")
	public TemplateEngine emailTemplateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		// Resolver para Emails con texto
		templateEngine.addTemplateResolver(textTemplateResolver());
		// Resolver para correos electr??nicos HTML (excepto los que sean editables)
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		// Resolver para correos electr??nicos HTML editables (que se tratar??n como un
		// String)
		templateEngine.addTemplateResolver(stringTemplateResolver());
		// Origen del mensaje, internacionalizaci??n espec??fica para correos electr??nicos
		templateEngine.setTemplateEngineMessageSource(emailMessageSource());
		return templateEngine;
	}

	// Definicion de la estructura de los correos con texto.
	private ITemplateResolver textTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(1));
		templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
		templateResolver.setPrefix("/mail/");
		templateResolver.setSuffix(".txt");
		templateResolver.setTemplateMode(TemplateMode.TEXT);
		templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	//Definicion de la estructura de los correos que contienen HTML
	private ITemplateResolver htmlTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(2));
		templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
		templateResolver.setPrefix("/mail/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	//Definicion de la estructura de los correos que tratan como una cadena tipo String
	private ITemplateResolver stringTemplateResolver() {
		final StringTemplateResolver templateResolver = new StringTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(3));
		// No resolvable pattern, will simply process as a String template everything
		// not previously matched
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

}
package com.ecopetrol.ECOrisk.Services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ecopetrol.ECOrisk.SpringMailConfig;
import com.sun.mail.imap.protocol.FLAGS;


@Service
public class EmailService {

    private static final String EMAIL_TEXT_TEMPLATE_NAME = "text/email-text";
    private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "html/email-simple";
    private static final String EMAIL_WITHATTACHMENT_TEMPLATE_NAME = "html/email-withattachment";
    private static final String EMAIL_CONTROLES = "html/email-controles.html";
    private static final String EMAIL_INDICADORES = "html/email-indicadores.html";
    private static final String EMAIL_INDICADORES_LIDERES= "html/email-indicadores-lideres.html";
    private static final String EMAIL_CAIDA_INTERRUPCION= "html/email-down.html";
    private static final String EMAIL_EDITABLE_TEMPLATE_CLASSPATH_RES = "classpath:mail/editablehtml/email-editable.html";
    private static final String EMAIL_INLINEIMAGE_TEMPLATE_NEW = "html/new-email.html";

    private static final String BACKGROUND_IMAGE = "mail/editablehtml/images/background.png";
    private static final String LOGO_BACKGROUND_IMAGE = "mail/editablehtml/images/logo-background.png";
    private static final String THYMELEAF_BANNER_IMAGE = "mail/editablehtml/images/thymeleaf-banner.png";
    private static final String THYMELEAF_LOGO_IMAGE = "mail/editablehtml/images/thymeleaf-logo.png";
    private static final String ECORISK_LOGO_IMAGE = "mail/html/images/logo.png";

    private static final String PNG_MIME = "image/png";
    

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier("email")
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    @Qualifier("email")
    private TemplateEngine textTemplateEngine;

    @Autowired
    @Qualifier("email")
    private TemplateEngine stringTemplateEngine;


    /* 
     * Send plain TEXT mail 
     */
    public void sendTextMail(
        final String recipientName, final String recipientEmail, final Locale locale)
        throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Example plain TEXT email");
        message.setFrom("thymeleaf@example.com");
        message.setTo(recipientEmail);

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_TEXT_TEMPLATE_NAME, ctx);
        message.setText(textContent);

        // Send email
        this.mailSender.send(mimeMessage);
    }


    /* 
     * Send HTML mail (simple) 
     */
    public void sendSimpleMail(
        final String recipientName, final String recipientEmail, final Locale locale)
        throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Example HTML email (simple)");
        message.setFrom("thymeleaf@example.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Send email
        this.mailSender.send(mimeMessage);
    }


    /* 
     * Send HTML mail with attachment. 
     */
    public void sendMailWithAttachment(
        final String recipientName, final String recipientEmail, final String attachmentFileName,
        final byte[] attachmentBytes, final String attachmentContentType, final Locale locale)
        throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
            = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Example HTML email with attachment");
        message.setFrom("thymeleaf@example.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_WITHATTACHMENT_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Add the attachment
        final InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
        message.addAttachment(
            attachmentFileName, attachmentSource, attachmentContentType);

        // Send mail
        this.mailSender.send(mimeMessage);
    }


    
    //Get all email folders name
    public void GetFoldersNames(Store store){
    	javax.mail.Folder[] folders;
		try {
			folders = store.getDefaultFolder().list("*");
			for (javax.mail.Folder folder : folders) {
		        if ((folder.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
		            System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
		        }
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
    }
    
    //Store in send elements
    public void EmailsEnviados(MimeMessage mimeMessage) {
    	/*  Set the mail properties  */
    	
    	Properties properties = System.getProperties();
    	Session session = Session.getDefaultInstance(properties);
    	String host = "manolo4000.com";
    	String user = "ecorisk@manolo4000.com";
    	String pwd =  "nvt7$@&46lC3";
    	try {
			Store store = session.getStore("imap");
			store.connect(host, user, pwd);  
			Folder folder = (Folder) store.getFolder("INBOX.Sent");
	        folder.open(Folder.READ_WRITE);
	        System.out.println("guardando...");
	        try {

	            folder.appendMessages(new Message[]{mimeMessage});
	            mimeMessage.setFlag(FLAGS.Flag.RECENT, true);
	            mimeMessage.setFlag(Flag.SEEN, true);

	        } catch (Exception ignore) {
	            System.out.println("error processing message " + ignore.getMessage());
	        } finally {
	            store.close();
	           // folder.close(false);
	        }
	        
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    }

    /*
     * Custom Mail
     */
    public void sendMailDown(
             final String Para,final String Asunto, final Context ctx)
            throws MessagingException {


        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
            = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject(Asunto);
        message.setFrom("ecorisk@ecopetrol.com.co");
        message.setTo(Para);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_CAIDA_INTERRUPCION, ctx);
        message.setText(htmlContent, true /* isHtml */);
        
        message.addInline("guytexting", new ClassPathResource("mail/html/images/Guy_texting.png"), PNG_MIME);
        message.addInline("logo", new ClassPathResource(ECORISK_LOGO_IMAGE), PNG_MIME);
        
        // Send mail
        this.mailSender.send(mimeMessage);
        
        EmailsEnviados(mimeMessage);
    }

    
    /*
     * Custom Mail
     */
    public void sendMailControles(
             final String Para,final String Asunto, final Context ctx)
            throws MessagingException {


        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
            = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject(Asunto);
        message.setFrom("ecorisk@ecopetrol.com.co");
        message.setTo(Para);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_CONTROLES, ctx);
        message.setText(htmlContent, true /* isHtml */);
        
        message.addInline("logo", new ClassPathResource(ECORISK_LOGO_IMAGE), PNG_MIME);
        
        // Send mail
        this.mailSender.send(mimeMessage);
        EmailsEnviados(mimeMessage);
    }
    
    /*
     * Custom Mail
     */
    public void sendMailIndicadores(
             final String Para,final String Asunto, final Context ctx)
            throws MessagingException {


        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
            = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject(Asunto);
        message.setFrom("ecorisk@ecopetrol.com.co");
        message.setTo(Para);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_INDICADORES, ctx);
        message.setText(htmlContent, true /* isHtml */);
        
        message.addInline("logo", new ClassPathResource(ECORISK_LOGO_IMAGE), PNG_MIME);
        
        // Send mail
        this.mailSender.send(mimeMessage);
        EmailsEnviados(mimeMessage);
    }
    
    /*
     * Custom Mail
     */
    public void sendMailIndicadoresLideres(
             final String Para,final String Asunto, final Context ctx)
            throws MessagingException {


        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
            = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject(Asunto);
        message.setFrom("ecorisk@ecopetrol.com.co");
        message.setTo(Para);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_INDICADORES_LIDERES, ctx);
        message.setText(htmlContent, true /* isHtml */);
        
        message.addInline("logo", new ClassPathResource(ECORISK_LOGO_IMAGE), PNG_MIME);
        
        // Send mail
        this.mailSender.send(mimeMessage);
        EmailsEnviados(mimeMessage);
    }
    
    public void sendMailSimple(
            final String Para,final String Asunto, final Context ctx)
           throws MessagingException {


       // Prepare message using a Spring helper
       final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
       final MimeMessageHelper message
           = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
       message.setSubject(Asunto);
       message.setFrom("ecorisk@ecopetrol.com.co");
       message.setTo(Para);

       // Create the HTML body using Thymeleaf
       final String htmlContent = this.htmlTemplateEngine.process(EMAIL_INLINEIMAGE_TEMPLATE_NEW, ctx);
       message.setText(htmlContent, true /* isHtml */);
       
       message.addInline("backgroundgreen", new ClassPathResource("mail/html/images/Background_green.png"), PNG_MIME);
       message.addInline("bottomsection", new ClassPathResource("mail/html/images/Bottom_section.png"), PNG_MIME);
       message.addInline("guytexting", new ClassPathResource("mail/html/images/Guy_texting.png"), PNG_MIME);
       message.addInline("logo", new ClassPathResource(ECORISK_LOGO_IMAGE), PNG_MIME);
       
       // Send mail
       this.mailSender.send(mimeMessage);
   }

    /* 
     * Send HTML mail with inline image
     */
    public String getEditableMailTemplate() throws IOException {
        final Resource templateResource = this.applicationContext.getResource(EMAIL_EDITABLE_TEMPLATE_CLASSPATH_RES);
        final InputStream inputStream = templateResource.getInputStream();
        return IOUtils.toString(inputStream, SpringMailConfig.EMAIL_TEMPLATE_ENCODING);
    }


    /*
     * Send HTML mail with inline image
     */
    public void sendEditableMail(
            final String recipientName, final String recipientEmail, final String htmlContent,
            final Locale locale)
            throws MessagingException {

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Example editable HTML email");
        message.setFrom("thymeleaf@example.com");
        message.setTo(recipientEmail);

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Create the HTML body using Thymeleaf
        final String output = stringTemplateEngine.process(htmlContent, ctx);
        message.setText(output, true /* isHtml */);

        // Add the inline images, referenced from the HTML code as "cid:image-name"
        message.addInline("background", new ClassPathResource(BACKGROUND_IMAGE), PNG_MIME);
        message.addInline("logo-background", new ClassPathResource(LOGO_BACKGROUND_IMAGE), PNG_MIME);
        message.addInline("thymeleaf-banner", new ClassPathResource(THYMELEAF_BANNER_IMAGE), PNG_MIME);
        message.addInline("thymeleaf-logo", new ClassPathResource(THYMELEAF_LOGO_IMAGE), PNG_MIME);

        // Send mail
        this.mailSender.send(mimeMessage);
    }

}
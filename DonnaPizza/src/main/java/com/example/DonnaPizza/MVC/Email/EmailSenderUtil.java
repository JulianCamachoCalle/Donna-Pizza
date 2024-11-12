package com.example.DonnaPizza.MVC.Email;

import org.apache.commons.mail.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
public class EmailSenderUtil {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    public String sendEmail(EmailDto emailDto) {
        try {
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(username, password));
            email.setSSLOnConnect(true);
            email.setFrom(username);
            email.setSubject(emailDto.getSubject());
            email.setMsg(emailDto.getMessage());
            email.addTo(emailDto.getReceiver());

            // Adjuntar archivo si existe
            MultipartFile attachmentFile = emailDto.getAttachment();
            if (attachmentFile != null && !attachmentFile.isEmpty()) {
                // Guardar el archivo en una ubicación temporal
                File tempFile = File.createTempFile("temp", attachmentFile.getOriginalFilename());
                attachmentFile.transferTo(tempFile);

                // Configurar el adjunto usando la ruta temporal
                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath(tempFile.getAbsolutePath());
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("Archivo adjunto");
                attachment.setName(attachmentFile.getOriginalFilename());
                email.attach(attachment);

                // Borrar el archivo temporal después de enviar el correo
                tempFile.deleteOnExit();
            }

            email.send();
            return "Email Sent";
        } catch (Exception exception) {
            exception.printStackTrace();
           return "Fail to send email";
        }
    }
}

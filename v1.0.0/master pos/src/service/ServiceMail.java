/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import javax.mail.Session;
import javax.mail.Authenticator;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import util.ModelMessage;

/**
 *
 * @author usER
 */
public class ServiceMail {
    public ModelMessage sendMail(String toEmail, String code) {
        ModelMessage ms = new ModelMessage(false, "");
//        String from = "mohaliakbarrafsanjani@gmail.com";
//        String from = "stevenaliakabar04@gmail.com";
        String from = "geulisofficialgroup@gmail.com";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.connectiontimeout", "5000");
        properties.put("mail.smtp.timeout", "5000");
//        String username = "mohaliakbarrafsanjani@gmail.com";
//        String username = "stevenaliakabar04@gmail.com";
        String username = "geulisofficialgroup@gmail.com";
//        String password = "rfcn cvsc oewe luwb";
//        String password = "lrea ewrq avtd gbtg";
        String password = "fbbd yqac nvno gkhn";
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Verifikasi Email");
            message.setText("Kode verifikasi anda adalah :" + code + "\nMohon untuk tidak memberikan kode ini kepada siapapun");
            Transport.send(message);
            ms.setSucces(true);
        } catch(MessagingException me) {
            if(me.getMessage().equals("Invalid Addresses")) {
                ms.setMessage("Email tidak sah");
            } else {
                ms.setMessage("Error");
                
            }
        }
        return ms;
    }
}

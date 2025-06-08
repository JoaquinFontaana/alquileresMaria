package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String body){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom("AlquileresMariaSupport@gmail.com");
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    @Async
    public void sendEmailsClientes(List<Cliente> clientes,String subject, String body){
        clientes.stream()
                .forEach(cliente -> this.sendEmail(cliente.getMail(),subject,body));
    }

    @Async
    public void sendNewPassword(String password, String mail){
        String subject = "Recuperar Contraseña";
        String body = "Su nueva contraseña es: "+ password;
        this.sendEmail(mail, subject, body);
    }

    @Async
    public void sendDobleAutenticacionAdmin(String mail, int cod){
        String subject = "Doble autenticación";
        String body = "El código es: " + cod;
        this.sendEmail(mail, subject, body);
    }
}

package inge2.com.alquileresMaria.configuration;

import inge2.com.alquileresMaria.service.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AdminCreation {
    private final UsuarioService usuarioService;
    @Value("${ADMIN_PASSWORD}")
    private static String adminPassword;
    @Value("${ADMIN_MAIL}")
    private static String adminMail;

    public AdminCreation(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void crearAdmin(){
        if(this.usuarioService.checkNotExistsAdmin()){
            this.usuarioService.crearAdmin(adminMail,adminPassword);
        }
    }

}

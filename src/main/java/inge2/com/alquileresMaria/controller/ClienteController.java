package inge2.com.alquileresMaria.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    @GetMapping(path = "/")
    public String hola(){
        return "Andando";
    }
    //Metodo para registrase
}

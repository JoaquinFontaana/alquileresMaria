package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.PersonaDTO;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cliente")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @GetMapping(path = "/")
    public String hola(){
        return "Andando";
    }
    //Metodo para registrase

    @PostMapping(path = "/registrar")
    public ResponseEntity<String> registrarCliente(@Valid @RequestBody PersonaDTO cliente){
        // Si falla la validaciónes o existe email/dni, se lanzará una RuntimeException
        service.crearCliente(cliente);

        return ResponseEntity.ok().body("Cliente creado con exito");

    }
}

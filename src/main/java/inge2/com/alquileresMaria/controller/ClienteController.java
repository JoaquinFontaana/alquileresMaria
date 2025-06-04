package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.PersonaDTO;
import inge2.com.alquileresMaria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cliente")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @PostMapping(path = "/registrar")
    public ResponseEntity<String> registrarCliente(@Valid @RequestBody PersonaDTO cliente){
        // Si falla la validaciónes o existe email/dni, se lanzará una RuntimeException
        service.crearCliente(cliente);

        return ResponseEntity.ok().body("Cliente creado con exito");

    }
    @GetMapping("/listar/alquileres")
    public List<AlquilerDTOListar> listarAlquileres(@RequestParam String email) {
        return this.service.listarAlquileres(email);
    }
    @GetMapping("/multa")
    public double multa(@RequestParam String mail){
        return this.service.getMulta(mail);
    }
}

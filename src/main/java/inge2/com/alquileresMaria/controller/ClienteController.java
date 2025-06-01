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

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping(path = "/registrar")
    public ResponseEntity<String> registrarCliente(@Valid @RequestBody PersonaDTO cliente){
        clienteService.crearCliente(cliente);
        return ResponseEntity.ok().body("Cliente creado con exito");
    }

    @GetMapping("/listar/alquileres")
    public List<AlquilerDTOListar> listarAlquileres(@RequestParam String email){
        return this.clienteService.listarAlquileres(email);
    }
}

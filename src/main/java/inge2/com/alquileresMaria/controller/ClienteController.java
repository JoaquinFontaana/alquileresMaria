package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.RembolsoDTO;
import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.user.PersonaDTOPassword;
import inge2.com.alquileresMaria.model.Rembolso;
import inge2.com.alquileresMaria.service.ClienteService;
import inge2.com.alquileresMaria.service.validators.AuthHelperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cliente")
public class ClienteController {
    private final AuthHelperService authHelperService;
    private final ClienteService service;
    @Autowired
    public ClienteController(AuthHelperService authHelperService, ClienteService service) {
        this.authHelperService = authHelperService;
        this.service = service;
    }

    @PostMapping(path = "/registrar")
    public ResponseEntity<String> registrarCliente(@Valid @RequestBody PersonaDTOPassword cliente){
        service.crearCliente(cliente);
        return ResponseEntity.ok().body("Cliente creado con exito");

    }
    @GetMapping("/listar/alquileres")
    public List<AlquilerDTOListar> listarAlquileres() {
        return this.service.listarAlquileres(authHelperService.getMailOfContext());
    }
    @GetMapping("/listar/rembolsos")
    public List<RembolsoDTO> listarRembolsos(){
        return this.service.listarRembolsos(this.authHelperService.getMailOfContext());
    }
    @GetMapping("/multa")
    public double multa(){
        return this.service.getMulta(this.authHelperService.getMailOfContext());
    }
}

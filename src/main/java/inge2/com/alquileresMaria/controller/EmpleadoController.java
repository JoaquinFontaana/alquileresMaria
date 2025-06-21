package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    private final ClienteService clienteService;

    public EmpleadoController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/registrar/cliente")
    public ResponseEntity<String> registrarCliente(@RequestBody @Valid PersonaDTO clienteDTO){
        this.clienteService.registrarClientePresencial(clienteDTO);
        return new ResponseEntity<>("Cliente registrado con exito", HttpStatus.CREATED);
    }
}

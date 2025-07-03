package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    @GetMapping("/existe/cliente")
    public ResponseEntity<String>  existeCliente(@RequestParam String mail, @RequestParam String dni) {
        this.clienteService.checkNotExistsCliente(mail,dni);
        return new ResponseEntity<>("El cliente no se encuentra registrado", HttpStatus.OK);
    }
}

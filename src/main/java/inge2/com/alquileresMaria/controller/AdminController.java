package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.service.ClienteService;
import inge2.com.alquileresMaria.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final EmpleadoService empleadoService;
    private final ClienteService clienteService;

    public AdminController(EmpleadoService empleadoService, ClienteService clienteService) {
        this.empleadoService = empleadoService;
        this.clienteService = clienteService;
    }

    @PostMapping(path = "/empleado/registrar")
    public ResponseEntity<String> crearEmpleado(@RequestBody @Valid EmpleadoDTO empleadoDTO){
        this.empleadoService.crearEmpleado(empleadoDTO);
        return new ResponseEntity<String>("Empleado registrado con exito", HttpStatus.CREATED);
    }

    @GetMapping("/listar/empleados")
    public List<EmpleadoDTO> listarEmpleados(){
        return this.empleadoService.listarEmpleados();
    }

    @GetMapping("/listar/clientes")
    public List<PersonaDTO> listarClientes(){
        return this.clienteService.listarClientes();
    }
    //TODO hacer un filtrado dinamico de alquileres como el de autos con decorator
    @GetMapping("/listar/alquileres/cliente")
    public List<AlquilerDTOListar> listarAlquileresCliente(@RequestParam String mail){
        return this.clienteService.listarAlquileres(mail);
    }
}

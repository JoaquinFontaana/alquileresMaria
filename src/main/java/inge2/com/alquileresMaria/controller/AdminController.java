package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import inge2.com.alquileresMaria.dto.user.EmpleadoDTOActualizar;
import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.service.ClienteService;
import inge2.com.alquileresMaria.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final EmpleadoService empleadoService;
    private final ClienteService clienteService;

    public AdminController(EmpleadoService empleadoService, ClienteService clienteService) {
        this.empleadoService = empleadoService;
        this.clienteService = clienteService;
    }

    @PostMapping("/empleado/registrar")
    public ResponseEntity<String> crearEmpleado(@RequestBody @Valid EmpleadoDTO empleadoDTO){
        this.empleadoService.crearEmpleado(empleadoDTO);
        return new ResponseEntity<String>("Empleado registrado con exito", HttpStatus.CREATED);
    }

    //Solo mandar los datos a actualizar y el mail actual del empleado (obligatorio)
    @PutMapping("/empleado/actualizar")
    public ResponseEntity<String> actualizarDatosEmpleado(@RequestBody @Valid EmpleadoDTOActualizar empleadoDTO){
        this.empleadoService.actualizarEmpleado(empleadoDTO);
        return new ResponseEntity<String>("Empleado actualizado con exito",HttpStatus.OK);
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
    @GetMapping("/listar/alquileres")
    public List<AlquilerDTOListar> listarAlquileres(@RequestParam String mail){
        return this.clienteService.listarAlquileres(mail);
    }
    @PutMapping("/empleado/eliminar")
    public ResponseEntity<String> eliminarEmpleado(@RequestParam String mail){
        this.empleadoService.eliminarEmpleado(mail);
        return new ResponseEntity<>("Empleado eliminado con exito", HttpStatus.OK);
    }
}

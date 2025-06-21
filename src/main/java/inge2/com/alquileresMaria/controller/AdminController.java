package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import inge2.com.alquileresMaria.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final EmpleadoService empleadoService;

    public AdminController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping(path = "/empleado/registrar")
    public ResponseEntity<String> crearEmpleado(@RequestBody @Valid EmpleadoDTO empleadoDTO){
        this.empleadoService.crearEmpleado(empleadoDTO);
        return new ResponseEntity<String>("Empleado registrado con exito", HttpStatus.OK);
    }
}

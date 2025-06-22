package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {
    @Autowired
    private SucursalService service;

    @PostMapping("/admin/crear")
    public ResponseEntity<String> crearSucursal(@Valid @RequestBody Sucursal sucursal){
        service.crearSucursal(sucursal);
        return ResponseEntity.ok("Sucursal creada con exito");
    }


    @GetMapping("/admin/empleados")
    public List<EmpleadoDTO> listarEmpleados(@Valid @RequestParam String ciudad){
        return this.service.listarEmpleadosSucursal(ciudad);
    }

    @GetMapping("/listar")
    public List<String> listarSucursales(){
        return service.listarSucursales();
    }
}

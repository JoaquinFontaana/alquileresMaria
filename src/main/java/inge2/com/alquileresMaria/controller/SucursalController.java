package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {
    @Autowired
    private SucursalService service;

    @PostMapping("/crear")
    public ResponseEntity<String> crearSucursal(@Valid @RequestBody Sucursal sucursal){
        service.crearSucursal(sucursal);
        return ResponseEntity.ok("Sucursal creada con exito");
    }
}

package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.service.SucursalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {
    private SucursalService service;

    @PostMapping("/crear")
    public Sucursal crearSucursal(@RequestBody Sucursal sucursal){
        return service.crearSucursal(sucursal);
    }
}

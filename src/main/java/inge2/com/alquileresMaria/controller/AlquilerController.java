package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alquiler")
public class AlquilerController {
    @Autowired
    private AlquilerService alquilerService;
    /*@PostMapping("/crear")
    public String crearReserva(@Valid @RequestBody AlquilerDTO alquilerDTO){
        this.alquilerService.crearReserva(alquilerDTO);
        return "Alquiler creado con exito";
    }*/

}

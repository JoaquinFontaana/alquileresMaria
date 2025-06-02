package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.AlquilerDTOListar;
import inge2.com.alquileresMaria.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alquiler")
public class AlquilerController {
    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/listar")
    public List<AlquilerDTOListar> listar() {
        return this.alquilerService.obtenerAlquileres();
    }

}

package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.ReservaDTOCancelar;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @PostMapping("/cancelarReserva")
    public ResponseEntity<String> cancelarReserva(@RequestBody ReservaDTOCancelar reserva){
        this.alquilerService.cancelarReserva(reserva);
        return ResponseEntity.ok("Reserva cancelada exitosamente");
    }

}

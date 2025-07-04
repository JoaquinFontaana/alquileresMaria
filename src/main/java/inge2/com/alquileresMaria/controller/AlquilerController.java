package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOFilter;
import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.alquiler.ReservaDTOFechaLicencia;
import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import inge2.com.alquileresMaria.model.enums.Extra;
import inge2.com.alquileresMaria.service.AlquilerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alquiler")
public class AlquilerController {
    @Autowired
    private AlquilerService alquilerService;

    @PostMapping("/cancelarReserva")
    public ResponseEntity<String> cancelarReserva(@Valid  @RequestBody ReservaDTOFechaLicencia reserva){
        this.alquilerService.cancelarReserva(reserva);
        return ResponseEntity.ok("Reserva cancelada exitosamente");
    }

    @GetMapping("/listar")
    public List<AlquilerDTOListar> filtrar(@ModelAttribute AlquilerDTOFilter opcionesFiltrado){
        return this.alquilerService.listarAlquileres(opcionesFiltrado);
    }
/*
    @PostMapping("/agregarExtra")
    public ResponseEntity<String> agregarExtra(List<Extra> extras, ReservaDTOFechaLicencia reserva){
        this.alquilerService.agregarExtra(extras, reserva);
    }

 */
    @GetMapping("/get/extras")
    public List<Extra> getExtras(){
        return List.of(Extra.values());
    }

}

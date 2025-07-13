package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.alquiler.*;
import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.model.enums.Extra;
import inge2.com.alquileresMaria.service.AlquilerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/alquiler")
public class AlquilerController {
    @Autowired
    private AlquilerService alquilerService;

    @PostMapping("/cancelarReserva")
    public ResponseEntity<String> cancelarReserva(@Valid  @RequestBody AlquilerDTOFechaLicencia reserva){
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

    @PostMapping("/empleado/entregarAlquiler")
    public ResponseEntity<String> entregarAlquiler(@Valid  @RequestBody AlquilerDTOFechaLicencia reserva){
        this.alquilerService.iniciarAlquiler(reserva);
        return ResponseEntity.ok("Alquiler iniciado exitosamente");
    }

    @PostMapping("/empleado/recibirAlquilerCorrecto")
    public ResponseEntity<String> recibirAlquilerCorrecto(@Valid @RequestBody AlquilerDTOFechaLicencia reservaDTO){
        this.alquilerService.finalizarAlquilerCorrecto(reservaDTO);
        return ResponseEntity.ok("Alquiler recibido exitosamente");
    }
    @PostMapping("/empleado/recibirAlquilerMulta")
    public ResponseEntity<String> recibirAlquilerMulta(@Valid @RequestBody MultaAlquilerDTO multaAlquilerDTO){
        this.alquilerService.finalizarAlquilerMantenimiento(multaAlquilerDTO);
        return ResponseEntity.ok("Alquiler recibido exitosamente");
    }

    @GetMapping("/empleado/pendientesRetiro")
    public List<AlquilerDTOListar> listarAlquileresPendientesRetiro(@Valid @RequestParam String sucursal){
        return this.alquilerService.listarPendientesEntrega(sucursal);
    }
    @GetMapping("/empleado/pendientesDevolucion")
    public List<AlquilerDTOListar> listarAlquileresPendientesDevolucion(@Valid @RequestParam String sucursal){
        return this.alquilerService.listarPendientesDevolucion(sucursal);
    }
    @GetMapping("/empleado/sugerirSimilares")
    public List<AutoDTOListar> sugerirSimilares(@Valid @RequestParam String licenciaConductor, @Valid @RequestParam LocalDate fechaDesde, @Valid @RequestParam LocalDate fechaHasta){
        return this.alquilerService.sugerirSimilares(licenciaConductor, fechaDesde, fechaHasta);
    }
    @PutMapping("/empleado/cambiarAuto")
    public ResponseEntity<String> cambiarAuto(@Valid @RequestBody AlquilerDTOCambiarAuto alquilerDTOCambiarAuto){
        this.alquilerService.cambiarAuto(alquilerDTOCambiarAuto);
        return ResponseEntity.ok("Auto cambiado exitosamente");
    }
}

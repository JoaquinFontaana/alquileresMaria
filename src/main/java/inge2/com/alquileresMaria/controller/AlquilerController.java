package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.alquiler.*;
import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.model.enums.Extra;
import inge2.com.alquileresMaria.service.AlquilerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    @PostMapping("/cancelar")
    public ResponseEntity<String> cancelarReserva(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @RequestParam Long codigoAlquiler){
        this.alquilerService.cancelarReserva(codigoAlquiler);
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
    public ResponseEntity<String> entregarAlquiler(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @RequestParam Long codigoAlquiler){
        this.alquilerService.iniciarAlquiler(codigoAlquiler);
        return ResponseEntity.ok("Alquiler iniciado exitosamente");
    }

    @PostMapping("/empleado/recibirAlquiler/correcto")
    public ResponseEntity<String> recibirAlquilerCorrecto(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @RequestParam Long codigoAlquiler){
        this.alquilerService.finalizarAlquilerCorrecto(codigoAlquiler);
        return ResponseEntity.ok("Alquiler recibido exitosamente");
    }
    @PostMapping("/empleado/recibirAlquiler/multa")
    public ResponseEntity<String> recibirAlquilerMulta(@Valid @RequestBody MultaAlquilerDTO multaAlquilerDTO){
        this.alquilerService.finalizarAlquilerMantenimiento(multaAlquilerDTO);
        return ResponseEntity.ok("Alquiler recibido exitosamente");
    }

    @GetMapping("/empleado/get/pendientesRetiro")
    public List<AlquilerDTOListar> listarAlquileresPendientesRetiro(@Valid @RequestParam String sucursal){
        return this.alquilerService.listarPendientesEntrega(sucursal);
    }
    @GetMapping("/empleado/get/pendientesDevolucion")
    public List<AlquilerDTOListar> listarAlquileresPendientesDevolucion(@Valid @RequestParam String sucursal){
        return this.alquilerService.listarPendientesDevolucion(sucursal);
    }
    @GetMapping("/empleado/get/similares")
    public List<AutoDTOListar> sugerirSimilares(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @RequestParam Long codigoAlquiler){
        return this.alquilerService.sugerirSimilares(codigoAlquiler);
    }

}

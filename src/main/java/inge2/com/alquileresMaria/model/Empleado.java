package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Empleado extends Persona {
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    @NotBlank
    private Sucursal trabajaEnSucursal;

}

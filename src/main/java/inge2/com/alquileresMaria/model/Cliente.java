package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Cliente extends Persona {

    @Min(value = 18, message = "El usuario debe ser mayor de edad")
    private int edad;
    @OneToMany(mappedBy = "cliente")
    private List<Alquiler> alquileres;
}

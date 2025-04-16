package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Cliente extends Persona {

    @Min(value = 18, message = "El usuario debe ser mayor de edad")
    @NotBlank(message = "La edad es obligatoria")
    private int edad;

}

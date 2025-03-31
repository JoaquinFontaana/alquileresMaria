package inge2.com.alquileresMaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Cliente extends Usuario{
    @Min(value = 18, message = "El usuario debe ser mayor de edad")
    @NotBlank(message = "La edad es obligatoria")
    private int edad;

}

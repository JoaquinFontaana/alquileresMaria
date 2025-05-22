package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.Persona;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDtoPassword extends PersonaDTO{

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;


    public PersonaDtoPassword(Persona persona) {
        super(persona);
        this.password = persona.getPassword();
    }
}

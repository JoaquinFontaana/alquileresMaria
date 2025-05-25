package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.Persona;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonaDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El mail es obligatorio")
    @Email(message = "Ingresa un email valido")
    private String mail;
    @Column(length = 9)
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 9, message = "El dni debe tener 8 o 9 caracteres")
    private String dni;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public PersonaDTO(Persona persona) {
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellido();
        this.mail = persona.getMail();
        this.dni = this.getDni();
    }
    public PersonaDTO(){

    }
}

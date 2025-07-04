package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.dto.user.PersonaDTOPassword;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass @Getter @Setter
public abstract class Persona extends Usuario {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El mail es obligatorio")
    @Column(length = 9)
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 9, message = "El dni debe tener 8 o 9 caracteres")
    private String dni;

    public void actualizarDatos(PersonaDTO personaDTO){
        if(personaDTO.getDni() != null){
            this.setDni(personaDTO.getDni());
        }
        if(personaDTO.getApellido() != null){
            this.setApellido(personaDTO.getApellido());
        }
        if(personaDTO.getNombre() != null){
            this.setNombre(personaDTO.getNombre());
        }
    }

    public Persona(PersonaDTOPassword dto, Rol rol) {
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.dni = dto.getDni();
        this.setMail(dto.getMail());
        this.setRol(rol);
        this.setPassword(dto.getPassword());
    }
    public Persona(PersonaDTO dto, Rol rol, String password) {
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.dni = dto.getDni();
        this.setMail(dto.getMail());
        this.setRol(rol);
        this.setPassword(password);
    }

    public Persona(){
        super();
    }
}

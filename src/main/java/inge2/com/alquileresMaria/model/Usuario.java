package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    @Email(message = "Ingresa un email valido")
    private String mail;
    @ManyToOne(optional = false)
    @JoinColumn(name = "rol_id")
    private Rol rol;
    public Usuario(){

    }

    public Usuario(String password, String mail, Rol rol) {
        this.password = password;
        this.mail = mail;
        this.rol = rol;
    }

    public void modificarPassword(String password){
        this.password = password;
    }

}

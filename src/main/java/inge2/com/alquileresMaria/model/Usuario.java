package inge2.com.alquileresMaria.model;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario extends Persona{
    private String nombre;
    private String apellido;
    private String mail;
    private String dni;
}

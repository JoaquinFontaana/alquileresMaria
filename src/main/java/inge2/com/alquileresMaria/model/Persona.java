package inge2.com.alquileresMaria.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Persona extends Usuario {
    private String nombre;
    private String apellido;
    private String mail;
    private String dni;
}

package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.*;
@Entity
public class Sucursal {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;
    @OneToMany(mappedBy = "sucursal")
    private List<Auto> autos;

    public String getCiudad() {
        return ciudad;
    }
}

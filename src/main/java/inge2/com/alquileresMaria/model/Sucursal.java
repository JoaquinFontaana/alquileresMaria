package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;

import java.util.*;
@Entity
public class Sucursal {
    @Id
    @GeneratedValue
    private String id;
    private String ciudad;
    @OneToMany(mappedBy = "sucursal")
    private List<Auto> autos;

    public String getCiudad() {
        return ciudad;
    }
}

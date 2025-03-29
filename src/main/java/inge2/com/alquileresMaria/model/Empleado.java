package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;

@Entity
public class Empleado extends Usuario{
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal trabajaEnSucursal;

}

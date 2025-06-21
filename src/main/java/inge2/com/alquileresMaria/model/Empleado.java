package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Empleado extends Persona {
    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal trabajaEnSucursal;

    public Empleado(EmpleadoDTO empleadoDTO, Sucursal trabajaEnSucursal,Rol rol, String password) {
        super(empleadoDTO,rol,password);
        this.trabajaEnSucursal = trabajaEnSucursal;
    }
    public Empleado(){
        super();
    }
}

package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.EmpleadoDTO;
import inge2.com.alquileresMaria.dto.PersonaDTO;
import inge2.com.alquileresMaria.dto.PersonaDtoPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Empleado extends Persona {
    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal trabajaEnSucursal;

    public Empleado(EmpleadoDTO empleadoDTO, Sucursal trabajaEnSucursal,Rol rol) {
        super((PersonaDtoPassword) empleadoDTO,rol);
        this.trabajaEnSucursal = trabajaEnSucursal;
    }
}

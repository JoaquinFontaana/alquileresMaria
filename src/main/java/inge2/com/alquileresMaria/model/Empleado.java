package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import inge2.com.alquileresMaria.dto.user.EmpleadoDTOActualizar;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Empleado extends Persona {
    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal trabajaEnSucursal;

    public void actualizarDatos(EmpleadoDTOActualizar empleadoDTO){
        if(empleadoDTO.getNuevoMail() != null){
            this.setMail(empleadoDTO.getNuevoMail());
        }
        super.actualizarDatos(empleadoDTO);
    }

    public void actualizarDatos(EmpleadoDTOActualizar empleadoDTO,Sucursal sucursal){
        this.actualizarDatos(empleadoDTO);
        this.setTrabajaEnSucursal(sucursal);
    }

    public Empleado(EmpleadoDTO empleadoDTO, Sucursal trabajaEnSucursal,Rol rol, String password) {
        super(empleadoDTO,rol,password);
        this.trabajaEnSucursal = trabajaEnSucursal;
    }
    public Empleado(){
        super();
    }
}

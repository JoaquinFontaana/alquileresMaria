package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import inge2.com.alquileresMaria.dto.user.EmpleadoDTOActualizar;
import inge2.com.alquileresMaria.model.enums.EstadoEmpleado;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Getter @Setter
public class Empleado extends Persona {
    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal trabajaEnSucursal;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEmpleado estado;
    private LocalDate fechaBaja;


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
        this.estado = EstadoEmpleado.ACTIVO;
        this.fechaBaja = null;
    }
    public Empleado(){
        super();
    }

    public void eliminar() {
        this.estado = EstadoEmpleado.INACTIVO;
        this.borrarDni();
        this.borrarMail();
        this.fechaBaja = LocalDate.now();
    }
}

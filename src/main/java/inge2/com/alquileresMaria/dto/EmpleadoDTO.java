package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.Empleado;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmpleadoDTO extends PersonaDTO{
    @NotNull(message = "La sucursal donde trabaja es obligatoria, ingresa el nombre")
    private String nombreSucursalTrabaja;


    public EmpleadoDTO(Empleado empleado){
        super(empleado);
        this.nombreSucursalTrabaja = empleado.getTrabajaEnSucursal().getCiudad();
    }


}

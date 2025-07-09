package inge2.com.alquileresMaria.dto.user;

import inge2.com.alquileresMaria.model.Empleado;
import inge2.com.alquileresMaria.model.enums.EstadoEmpleado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmpleadoDTO extends PersonaDTO{
    @NotNull(message = "La sucursal donde trabaja el empleado es obligatoria, ingresa el nombre")
    private String trabajaEnSucursal;
    @NotBlank
    private EstadoEmpleado estado;

    public EmpleadoDTO(Empleado empleado){
        super(empleado);
        this.trabajaEnSucursal = empleado.getTrabajaEnSucursal().getCiudad();
        this.estado = EstadoEmpleado.ACTIVO;
    }
    public EmpleadoDTO(){
        super();
    }
}

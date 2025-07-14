package inge2.com.alquileresMaria.dto.user;

import inge2.com.alquileresMaria.model.Empleado;
import inge2.com.alquileresMaria.model.enums.EstadoEmpleado;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class EmpleadoDTOListar extends EmpleadoDTO{
    @NotNull
    private EstadoEmpleado estado;
    private LocalDate fechaBaja;

    public EmpleadoDTOListar(Empleado empleado) {
        super(empleado);
        this.estado = empleado.getEstado();
        this.fechaBaja = empleado.getFechaBaja();
    }

    public EmpleadoDTOListar() { super(); }

}

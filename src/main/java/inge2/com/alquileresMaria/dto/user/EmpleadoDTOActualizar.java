package inge2.com.alquileresMaria.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmpleadoDTOActualizar extends PersonaDTOActualizar{
    private String nuevaSucursal;

    public EmpleadoDTOActualizar() {
        super();
    }
}

package inge2.com.alquileresMaria.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmpleadoDTOActualizar extends EmpleadoDTO{
    private String nuevoMail;

    public EmpleadoDTOActualizar() {
        super();
    }
}

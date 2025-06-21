package inge2.com.alquileresMaria.dto.alquiler;

import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlquilerDTOCrear {
    @Embedded
    private RangoFecha rangoFecha;
    @NotBlank
    private String licenciaConductor;
    @NotBlank
    private String patenteAuto;
    @NotBlank
    private String sucursal;

}

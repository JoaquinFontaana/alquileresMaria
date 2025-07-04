package inge2.com.alquileresMaria.dto.alquiler;

import inge2.com.alquileresMaria.model.enums.Extra;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @NotBlank
    private List<Extra> extras;

}

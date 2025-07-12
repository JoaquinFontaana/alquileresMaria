package inge2.com.alquileresMaria.dto.alquiler;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlquilerDTOCambiarAuto extends AlquilerDTOFechaLicencia{
    @NotNull (message = "La patente del auto nuevo es obligatoria")
    private String patenteAutoNuevo;
}

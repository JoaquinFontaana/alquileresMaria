package inge2.com.alquileresMaria.dto.alquiler;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AlquilerDTOFechaLicencia {
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaDesde;
    @NotNull(message = "La licencia del conductor es obligatoria")
    private String licencia;

    public AlquilerDTOFechaLicencia(LocalDate fechaDesde, LocalDate fechaFin, String licencia) {
        this.fechaDesde = fechaDesde;
        this.licencia = licencia;
        this.fechaFin = fechaFin;
    }

    public AlquilerDTOFechaLicencia() {
    }
}

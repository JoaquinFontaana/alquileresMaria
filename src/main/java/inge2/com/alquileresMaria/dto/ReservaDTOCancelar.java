package inge2.com.alquileresMaria.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter @Setter
public class ReservaDTOCancelar {
    private LocalDate fechaFin;
    private LocalDate fechaDesde;
    private String licenciaCliente;

    public ReservaDTOCancelar(LocalDate fechaDesde, LocalDate fechaFin, String licenciaCliente) {
        this.fechaDesde = fechaDesde;
        this.licenciaCliente = licenciaCliente;
    }
}

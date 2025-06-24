package inge2.com.alquileresMaria.dto.alquiler;

import inge2.com.alquileresMaria.model.Rembolso;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter @Setter
public class RembolsoDTO {

    private LocalDate fechaRembolso;
    private double montoRembolsado;

    public RembolsoDTO(Rembolso rembolso){
        this.montoRembolsado = rembolso.getMontoRembolsado();
        this.fechaRembolso = rembolso.getFechaRembolso();
    }

    public RembolsoDTO() {
    }
}

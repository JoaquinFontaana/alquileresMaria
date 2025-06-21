package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileresMaria.model.Rembolso;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter @Setter
public class RembolsoDTO {

    private LocalDate fechaRembolso;
    private double montoRembolsado;
    private AlquilerDTOListar alquilerRembolsado;

    public RembolsoDTO(Rembolso rembolso){
        this.montoRembolsado = rembolso.getMontoRembolsado();
        this.fechaRembolso = rembolso.getFechaRembolso();
        this.alquilerRembolsado = new AlquilerDTOListar(rembolso.getAlquilerRembolsado());
    }

    public RembolsoDTO() {
    }
}

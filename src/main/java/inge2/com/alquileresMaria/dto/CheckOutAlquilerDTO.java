package inge2.com.alquileresMaria.dto;

import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CheckOutAlquilerDTO {
    @Embedded
    private DatosPagoDTO datosPagoDTO;
    @Embedded
    private AlquilerDTOCrear alquilerDTO;

}

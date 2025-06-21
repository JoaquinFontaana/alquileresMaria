package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOCrear;
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

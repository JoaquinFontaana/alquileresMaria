package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.RangoFecha;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlquilerDTOCrear {
    @Embedded
    private RangoFecha rangoFecha;
    private String licenciaConductor;
    private String clienteMail;
    private String patenteAuto;
    private String sucursalEntrega;
    private String sucursalDevolucion;

}

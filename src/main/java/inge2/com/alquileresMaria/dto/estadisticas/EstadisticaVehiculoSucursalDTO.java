package inge2.com.alquileresMaria.dto.estadisticas;

import inge2.com.alquileresMaria.dto.auto.AutoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EstadisticaVehiculoSucursalDTO {
    private String sucursal;
    private AutoDTO auto;
    private Long cantidad;

    // Getters y setters
}
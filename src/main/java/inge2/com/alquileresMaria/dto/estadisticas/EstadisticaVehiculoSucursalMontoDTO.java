package inge2.com.alquileresMaria.dto.estadisticas;

import inge2.com.alquileresMaria.model.Auto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class EstadisticaVehiculoSucursalMontoDTO {
    private String ciudad;
    private Auto auto;
    private Long cantidadAlquileres;
    private Double montoTotal;
}

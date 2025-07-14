package inge2.com.alquileresMaria.dto.estadisticas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstadisticaIngresoSucursalCantidadDTO {
    private String ciudad;
    private Double montoTotal;
    private Long cantidadAlquileres;
}

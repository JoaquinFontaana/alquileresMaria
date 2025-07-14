package inge2.com.alquileresMaria.dto.estadisticas;

import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class EstadisticaCategoriaMontoDTO {
    private CategoriaAuto categoria;
    private Long cantidadAlquileres;
    private Double montoTotal;

}

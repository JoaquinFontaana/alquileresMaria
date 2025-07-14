package inge2.com.alquileresMaria.dto.estadisticas;

import inge2.com.alquileresMaria.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class EstadisticaClienteMontoDTO {
    private Cliente cliente;
    private Long cantidadAlquileres;
    private Double montoGastado;
}

package inge2.com.alquileresMaria.dto.estadisticas;

import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadisticaClienteDTO {
    private PersonaDTO cliente;
    private Long cantidadReservas;

    // Getters y setters
}
package inge2.com.alquileresMaria.dto.estadisticas;

import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadisticaCategoriaDTO {
    private CategoriaAuto categoria;
    private Long cantidad;

}
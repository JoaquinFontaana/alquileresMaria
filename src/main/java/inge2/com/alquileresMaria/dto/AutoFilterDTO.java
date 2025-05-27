package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import inge2.com.alquileresMaria.service.filter.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class AutoFilterDTO {
    private String nombreSucursal;
    private RangoFecha rangoFechas;
    private Integer capacidad;
    private List<CategoriaAuto> categorias;
    public AutoFilterDTO(String nombreSucursal, RangoFecha rangoFecha, Integer capacidad, List<CategoriaAuto> categorias) {
        this.nombreSucursal = nombreSucursal;
        this.rangoFechas = rangoFecha;
        this.capacidad = capacidad;
        this.categorias = categorias;
    }

}

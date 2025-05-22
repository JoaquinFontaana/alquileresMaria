package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.CategoriaAuto;
import inge2.com.alquileresMaria.model.RangoFecha;
import inge2.com.alquileresMaria.service.Filter.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
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

    /**
     * Construye la cadena de filtros (IAutoFilter) envolviendo
     * el baseFilter en los decoradores correspondientes.
     */
    public IAutoFilter buildFilter(IAutoFilter baseFilter) {
        IAutoFilter filtro = baseFilter;

        if (this.nombreSucursal != null) {
            filtro = new SucursalFilterDecorator(filtro, this.nombreSucursal);
        }

        if (this.rangoFechas != null) {
            filtro = new FechaFilterDecorator(filtro, this.rangoFechas);
        }

        if (this.capacidad != null) {
            filtro = new CapacidadFilterDecorator(filtro, this.capacidad);
        }

        if (this.categorias != null) {
            filtro = new CategoriaFilterDecorator(filtro, this.categorias);
        }

        return filtro;
    }
}

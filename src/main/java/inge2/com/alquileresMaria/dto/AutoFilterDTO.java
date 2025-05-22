package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.CategoriaAuto;
import inge2.com.alquileresMaria.model.RangoFecha;
import inge2.com.alquileresMaria.service.Filter.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class AutoFilterDTO {
    @NotBlank(message= "El nombre no puede estar vacio")
    private String nombreSucursal;
    private RangoFecha rangoFechas;
    @Positive(message = "La capacidad debe ser mayor a cero")
    private Integer capacidad;
    @NotEmpty(message = "no mandar lista si no hay categorias para filtrar")
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

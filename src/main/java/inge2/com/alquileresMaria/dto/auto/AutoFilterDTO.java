package inge2.com.alquileresMaria.dto.auto;

import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import inge2.com.alquileresMaria.model.enums.EstadoAuto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter @Setter
public class AutoFilterDTO {
    private String nombreSucursal;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private Integer capacidad;
    private List<CategoriaAuto> categorias;
    private List<EstadoAuto> estadoAuto;
    public AutoFilterDTO(String nombreSucursal, LocalDate fechaDesde, LocalDate fechaHasta, Integer capacidad, List<CategoriaAuto> categorias, List<EstadoAuto> estadoAuto) {
        this.nombreSucursal = nombreSucursal;
        this.fechaDesde = fechaDesde ;
        this.fechaHasta = fechaHasta;
        this.capacidad = capacidad;
        this.categorias = categorias;
        this.estadoAuto = estadoAuto;
    }

}

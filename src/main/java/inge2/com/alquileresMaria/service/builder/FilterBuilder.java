package inge2.com.alquileresMaria.service.builder;

import inge2.com.alquileresMaria.dto.AutoFilterDTO;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import inge2.com.alquileresMaria.service.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public final class FilterBuilder {
    @Autowired
    private BaseAutoFilter baseAutoFilter;
    /**
     * Construye la cadena de filtros (IAutoFilter) envolviendo
     * el baseFilter en los decoradores correspondientes.
     */
    public IAutoFilter buildFilter(AutoFilterDTO autoFilterDTO) {
        IAutoFilter filtro = this.baseAutoFilter;

        if (autoFilterDTO.getNombreSucursal() != null) {
            filtro = new SucursalFilterDecorator(filtro, autoFilterDTO.getNombreSucursal());
        }

        if (autoFilterDTO.getFechaHasta() != null && autoFilterDTO.getFechaDesde() != null) {
            RangoFecha rangoFecha =new RangoFecha(autoFilterDTO.getFechaDesde(),autoFilterDTO.getFechaHasta());
            filtro = new DisponibilidadFilterDecorator(filtro, rangoFecha);
        }

        if (autoFilterDTO.getCapacidad() != null) {
            filtro = new CapacidadFilterDecorator(filtro, autoFilterDTO.getCapacidad());
        }

        if (autoFilterDTO.getCategorias() != null) {
            filtro = new CategoriaFilterDecorator(filtro, autoFilterDTO.getCategorias());
        }

        if (autoFilterDTO.getEstadoAuto() != null){
            filtro = new EstadoFilterDecorator(filtro, autoFilterDTO.getEstadoAuto());
        }

        return filtro;
    }
}

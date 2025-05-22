package inge2.com.alquileresMaria.service.Filter;

import inge2.com.alquileresMaria.dto.AutoDtoListar;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.RangoFecha;

import java.util.List;

public class FechaFilterDecorator extends FilterDecorator{
    private RangoFecha rangoFechaFilter;

    public FechaFilterDecorator(IAutoFilter componente,RangoFecha rangoFechaFilter) {
        super(componente);
        this.rangoFechaFilter = rangoFechaFilter;
    }

    @Override
    public List<Auto> filtrar(List<Auto> autos) {
        return autos.stream()
                .filter(auto ->
                        auto.getReservas().stream()
                                .allMatch(reserva -> reserva.disponibleEnRangoFechas(rangoFechaFilter))
                )
                .toList();
    }

}

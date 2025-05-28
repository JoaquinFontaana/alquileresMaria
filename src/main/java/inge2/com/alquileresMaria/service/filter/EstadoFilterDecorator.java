package inge2.com.alquileresMaria.service.filter;

import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import inge2.com.alquileresMaria.model.enums.EstadoAuto;

import java.util.List;

public class EstadoFilterDecorator extends FilterDecorator{

    private List<EstadoAuto> estadosFilter;

    public EstadoFilterDecorator(IAutoFilter componente, List<EstadoAuto> estadosFilter) {
        super(componente);
        this.estadosFilter = estadosFilter;
    }

    @Override
    public List<Auto> filtrar(List<Auto> autos) {
        return autos.stream()
                .filter(auto -> this.estadosFilter.contains(auto.getEstado()))
                .toList();
    }
}

package inge2.com.alquileresMaria.service.Filter;

import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.CategoriaAuto;

import java.util.List;

public class CategoriaFilterDecorator extends FilterDecorator{
    List<CategoriaAuto> categoriasFilter;

    public CategoriaFilterDecorator(IAutoFilter componente, List<CategoriaAuto> categoriasFilter) {
        super(componente);
        this.categoriasFilter = categoriasFilter;
    }

    @Override
    public List<Auto> filtrar(List<Auto> autos) {
        return autos.stream()
                .filter(auto -> this.categoriasFilter.contains(auto.getCategoria()))
                .toList();
    }
}

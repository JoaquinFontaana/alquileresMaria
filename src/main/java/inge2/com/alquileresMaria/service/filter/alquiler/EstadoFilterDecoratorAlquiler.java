package inge2.com.alquileresMaria.service.filter.alquiler;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.enums.EstadoAlquiler;

import java.util.List;

public class EstadoFilterDecoratorAlquiler extends AlquilerDecorator{

    private List<EstadoAlquiler> estadoAlquiler;

    public EstadoFilterDecoratorAlquiler(AlquilerFilterComponent alquilerFilterComponent, List<EstadoAlquiler> estadoAlquiler) {
        super(alquilerFilterComponent);
        this.estadoAlquiler = estadoAlquiler;
    }

    @Override
    public List<Alquiler> filtrar(List<Alquiler> alquileres) {
        return alquileres.stream()
                .filter(a -> this.estadoAlquiler.contains(a.getEstadoAlquiler()))
                .toList();
    }
}

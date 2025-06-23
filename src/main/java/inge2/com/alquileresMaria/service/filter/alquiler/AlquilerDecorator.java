package inge2.com.alquileresMaria.service.filter.alquiler;

import inge2.com.alquileresMaria.model.Alquiler;

import java.util.List;

public abstract class AlquilerDecorator implements AlquilerFilterComponent{

    private AlquilerFilterComponent alquilerFilterComponent;

    public List<Alquiler> getAlquileres(){
        List<Alquiler> listaPrevia = this.alquilerFilterComponent.getAlquileres();
        return this.filtrar(listaPrevia);
    }
    public abstract List<Alquiler> filtrar(List<Alquiler> alquileres);

    public AlquilerDecorator(AlquilerFilterComponent alquilerFilterComponent) {
        this.alquilerFilterComponent = alquilerFilterComponent;
    }
}

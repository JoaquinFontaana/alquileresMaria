package inge2.com.alquileresMaria.service.filter.alquiler;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.service.filter.auto.SucursalFilterDecorator;

import java.util.List;

public class SucursalFilterDecoratorAlquiler extends AlquilerDecorator{
    private String nombreSucursal;

    public SucursalFilterDecoratorAlquiler(AlquilerFilterComponent component, String nombreSucursal){
        super(component);
        this.nombreSucursal = nombreSucursal;
    }

    @Override
    public List<Alquiler> filtrar(List<Alquiler> alquileres) {
        return alquileres.stream()
                .filter( alquiler -> alquiler.getSucursal().getCiudad().equals(nombreSucursal))
                .toList();
    }

}

package inge2.com.alquileresMaria.service.validators;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.enums.EstadoAuto;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoHelperService {
    @Autowired
    private IAutoRepository autoRepository;

    public void verificarDisponibilidad(Auto auto, RangoFecha rangoFecha){
        if(auto.getEstado() == EstadoAuto.BAJA || auto.getEstado() == EstadoAuto.EN_MANTENIMIENTO || !auto.disponibleEnRangoFechas(rangoFecha)){
            throw new IllegalStateException("El auto no se encuentra disponible en esas fechas");
        }
    }

    public void checkPatenteNotExists(String patente) {
        if (autoRepository.existsByPatente(patente)) {
            throw new EntityExistsException("La patente " + patente + " ya se encuentra registrada");
        }
    }

    public void checkPatenteExists(String patente) {
        if (autoRepository.existsByPatente(patente)) {
            throw new EntityExistsException("La patente " + patente + " no se encuentra registrada");
        }
    }

    public Auto findAutoByPatente(String patente) {
        return autoRepository.findByPatente(patente)
                .orElseThrow(() -> new EntityNotFoundException("La patente " + patente + " no existe"));
    }

    public void checkAutoNotAlquilado(Auto auto) {
        if (auto.getEstado() == EstadoAuto.ALQUILADO) {
            throw new IllegalStateException("El vehiculo con patente " + auto.getPatente() + " esta alquilado en este momento");
        }
    }
    public void checkAutoDisponible(Auto auto) {
        if (!auto.estaDisponible()) {
            throw new RuntimeException("El auto no esta disponible para alquilar");
        }
    }
    public void checkAutoNoDisponible(Auto auto) {
        if (auto.estaDisponible()) {
            throw new RuntimeException("El auto se encuentra disponible no hace falta cambiarlo");
        }
    }
    public List<Auto> findSimilaresPorPrecioOCategoria(Auto auto) {
        return autoRepository.findSimilaresPorPrecioOCategoria(auto.getSucursal().getId(), auto.getPrecioPorDia(), 2000, auto.getCategoria(), auto.getId());
    }
}

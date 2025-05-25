package inge2.com.alquileresMaria.service.Verfication;

import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.EstadoAuto;
import inge2.com.alquileresMaria.model.RangoFecha;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerficacionAutoService {
    @Autowired
    private IAutoRepository autoRepository;

    public void verificarDisponibilidad(Auto auto, RangoFecha rangoFecha){
        if(auto.getReservas().stream().anyMatch(alquiler -> !alquiler.disponibleEnRangoFechas(rangoFecha))){
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
}

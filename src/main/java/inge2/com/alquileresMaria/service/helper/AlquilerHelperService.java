package inge2.com.alquileresMaria.service.helper;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlquilerHelperService {

    private final IAlquilerRepository repository;
    @Autowired
    public AlquilerHelperService(IAlquilerRepository repository) {
        this.repository = repository;
    }

    public List<Alquiler> filtrarAlquileresPosteriores(List<Alquiler> alquileres){
        return  alquileres.stream()
                .filter(alquiler -> alquiler.getRangoFecha().getFechaDesde().isAfter(LocalDate.now()))
                .toList();
    }
    public List<Cliente> obtenerClientesDeAlquileres(List<Alquiler> alquileres){
        return  alquileres.stream().map(Alquiler::getCliente).toList();
    }
    public List<Long> obtenerIdsDeAlquileres(List<Alquiler> alquileres){
        return  alquileres.stream().map(Alquiler::getId).toList();
    }
    public void checkDisponibilidadConductor(RangoFecha rangoFecha,String licencia){
        this.repository.findAlquilerByLicenciaConductorAndRangoFecha(licencia,rangoFecha.getFechaDesde(),rangoFecha.getFechaHasta())
                .ifPresent(a ->{
                    throw new EntityExistsException("El conductor con licencia " + licencia +  " ya tiene un alquiler en el rango de fecha solicitado");
                });
    }
}

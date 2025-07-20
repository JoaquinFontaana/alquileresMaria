package inge2.com.alquileresMaria.service.validators;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import inge2.com.alquileresMaria.service.EmailService;
import inge2.com.alquileresMaria.service.RembolsoService;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlquilerHelperService {

    private final IAlquilerRepository repository;

    public AlquilerHelperService(IAlquilerRepository repository) {
        this.repository = repository;
    }

    public void checkDisponibilidadConductor(RangoFecha rangoFecha,String licencia){
        this.repository.findAlquilerByLicenciaConductorAndRangoFecha(licencia,rangoFecha.getFechaDesde(),rangoFecha.getFechaHasta())
                .ifPresent(a ->{
                    throw new EntityExistsException("El conductor con licencia " + licencia +  " ya tiene un alquiler en el rango de fecha solicitado");
                });
    }

    public void checkDuracionAlquiler(RangoFecha rangoFecha){
        if(rangoFecha.cantidadDeDias() > 14){
            throw new IllegalArgumentException("La duracion maxima de un alquiler son 14 dias");
        }
    }

    public Alquiler findById(Long codigoAlquiler) {
        return this.repository.findById(codigoAlquiler)
                .orElseThrow(() -> new IllegalArgumentException("El alquiler con codigo " + codigoAlquiler + " no existe"));
    }
}


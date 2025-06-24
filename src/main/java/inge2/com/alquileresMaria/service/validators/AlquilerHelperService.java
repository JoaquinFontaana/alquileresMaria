package inge2.com.alquileresMaria.service.validators;

import inge2.com.alquileresMaria.dto.alquiler.ReservaDTOCancelar;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.enums.EstadoAlquiler;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import inge2.com.alquileresMaria.service.EmailService;
import inge2.com.alquileresMaria.service.RembolsoService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlquilerHelperService {

    private final IAlquilerRepository repository;
    private final EmailService serviceEmail;
    private final RembolsoService rembolsoService;

    public AlquilerHelperService(IAlquilerRepository repository, EmailService serviceEmail, RembolsoService rembolsoService) {
        this.repository = repository;
        this.serviceEmail = serviceEmail;
        this.rembolsoService = rembolsoService;
    }

    public List<Alquiler> filtrarAlquileresPosteriores(List<Alquiler> alquileres){
        return  alquileres.stream()
                .filter(alquiler -> alquiler.esPosterior(LocalDate.now()))
                .toList();
    }

    public List<Cliente> obtenerClientesDeAlquileres(List<Alquiler> alquileres){
        return  alquileres.stream().map(Alquiler::getCliente).toList();
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

    public void rembolsarReservasPagadas(List<Alquiler> alquileres){
        alquileres.forEach(a -> {
            a.setEstadoAlquiler(EstadoAlquiler.CANCELADO);
            this.rembolsoService.crearRembolsoTotal(a);
        });
        this.repository.saveAll(alquileres);

        String subject = "Su vehiculo reservado ya no se encuentra disponible";
        String body = "Se ha rembolsado el total del alquiler";

        this.serviceEmail.sendEmailsClientes(this.obtenerClientesDeAlquileres(alquileres), subject,body);
    }
    public void eliminarAlquileres(List<Alquiler> alquileres){
        this.repository.deleteAll(alquileres);

        String subject = "Su vehiculo con reserva pendiente ya no se encuentra disponible";
        String body = "Se ha cancelado su reserva";

        this.serviceEmail.sendEmailsClientes(this.obtenerClientesDeAlquileres(alquileres), subject,body);
    }
    public Alquiler findByConductorRangoFechas(ReservaDTOCancelar reservaDTO){
        return repository.findAlquilerByLicenciaConductorAndRangoFecha(reservaDTO.getLicencia(), reservaDTO.getFechaDesde(),
                        reservaDTO.getFechaFin())
                .orElseThrow(() -> new EntityNotFoundException("No existe una reserva para este cliente, en esta fecha"));
    }

    public void checkForCancelacion(Alquiler reserva){
        if(reserva.getEstadoAlquiler() != EstadoAlquiler.PENDIENTE && reserva.getEstadoAlquiler() != EstadoAlquiler.CONFIRMACION_PENDIENTE){
            throw new IllegalStateException("La reserva no se puede cancelar en el estado actual en el que se encuentra");
        }
    }
}


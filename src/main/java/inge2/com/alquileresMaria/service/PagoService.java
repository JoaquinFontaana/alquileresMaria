package inge2.com.alquileresMaria.service;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Pago;
import inge2.com.alquileresMaria.model.enums.EstadoAlquiler;
import inge2.com.alquileresMaria.model.enums.EstadoPago;
import inge2.com.alquileresMaria.repository.IPagoRepository;
import inge2.com.alquileresMaria.service.validators.AlquilerHelperService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class PagoService {

    private final AlquilerHelperService alquilerHelperService;
    private final IPagoRepository pagoRepository;

    public PagoService(AlquilerHelperService alquilerHelperService, IPagoRepository pagoRepository) {
        this.alquilerHelperService = alquilerHelperService;
        this.pagoRepository = pagoRepository;
    }

    @Transactional
    public Pago crearPago(Preference preference, Alquiler alquiler){
        Pago pago = new Pago(preference,alquiler);
        return this.pagoRepository.save(pago);
    }

    @Transactional
    public void registrarCobro(Payment payment){
        Pago pago = this.findPagoByAlquilerId(Long.parseLong(payment.getExternalReference()));

        pago.setPaymentId(payment.getId());
        pago.setEstadoPago(EstadoPago.PAGADO);
        pago.getAlquiler().setEstadoAlquiler(EstadoAlquiler.PENDIENTE);

        this.pagoRepository.save(pago);
    }


    private Pago findPagoByAlquilerId(Long alquilerId){
        return pagoRepository.findByAlquiler_id(alquilerId)
                .orElseThrow(() -> new EntityNotFoundException("El pago con el alquilerId " + alquilerId + " no existe"));
    }

    @Transactional
    public void deletePagosPendientes(){
        List<Pago> pagos = this.pagoRepository.findPagosExpirados(OffsetDateTime.now(ZoneOffset.UTC), EstadoPago.PENDIENTE);
        this.alquilerHelperService.eliminarAlquileres(
                pagos.stream()
                .map(Pago::getAlquiler)
                .toList()
        );
    }

}

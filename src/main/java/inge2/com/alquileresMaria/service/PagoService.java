package inge2.com.alquileresMaria.service;

import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Pago;
import inge2.com.alquileresMaria.model.enums.EstadoPago;
import inge2.com.alquileresMaria.repository.IPagoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    @Autowired
    private IPagoRepository pagoRepository;

    @Transactional
    public Pago crearPago(Preference preference, Alquiler alquiler){
        Pago pago = new Pago(preference,alquiler);
        return this.pagoRepository.save(pago);
    }

    @Transactional
    public void registrarCobro(Long alquilerId){
        Pago pago = this.findPagoByAlquilerId(alquilerId);
        pago.setEstadoPago(EstadoPago.PAGADO);
        this.actualizarPago(pago);
    }

    private void actualizarPago(Pago pago){
        pagoRepository.save(pago);
    }

    private Pago findPagoByAlquilerId(Long alquilerId){
        return pagoRepository.findByAlquiler_id(alquilerId)
                .orElseThrow(() -> new EntityNotFoundException("El pago con el alquilerId " + alquilerId + " no existe"));
    }
}

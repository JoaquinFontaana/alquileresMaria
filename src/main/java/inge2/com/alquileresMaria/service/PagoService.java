package inge2.com.alquileresMaria.service;

import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Pago;
import inge2.com.alquileresMaria.repository.IPagoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    @Autowired
    private IPagoRepository pagoRepository;

    @Transactional
    public void crearPago(Preference preference, Alquiler alquiler){
        Pago pago = new Pago(preference.getId(),alquiler,preference.getSandboxInitPoint(),alquiler.calcularTotal());
        this.pagoRepository.save(pago);
    }

}

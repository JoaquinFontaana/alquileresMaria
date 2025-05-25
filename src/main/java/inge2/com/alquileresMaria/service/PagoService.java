package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Pago;
import inge2.com.alquileresMaria.repository.IPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    @Autowired
    private IPagoRepository pagoRepository;

    public void crearPago(Pago pago){
        this.pagoRepository.save(pago);
    }
}

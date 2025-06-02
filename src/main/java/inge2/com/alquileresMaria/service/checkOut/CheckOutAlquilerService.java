package inge2.com.alquileresMaria.service.checkOut;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.dto.CheckOutAlquilerDTO;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.PagoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckOutAlquilerService extends AbstractCheckOutService {
    @Autowired
    private PagoService pagoService;
    @Autowired
    private AlquilerService alquilerService;

    @Override
    public void procesarPagoAprobado(String externalReference) {
        this.pagoService.registrarCobro(externalReference);
    }

    @Transactional
    public String registrarAlquiler(CheckOutAlquilerDTO checkOutAlquilerDTO) throws MPException, MPApiException {
        Alquiler alquiler = this.alquilerService.crearAlquiler(checkOutAlquilerDTO.getAlquilerDTO());

        Preference preference = this.getMpPreferenceBuilder().crearPreferenceAlquiler(alquiler,checkOutAlquilerDTO.getDatosPagoDTO());

        return pagoService.crearPago(preference,alquiler).getInitPoint();//url de pago generada a partir de los datos obtenidos
    }
}

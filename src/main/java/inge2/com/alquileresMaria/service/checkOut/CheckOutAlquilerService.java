package inge2.com.alquileresMaria.service.checkOut;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.dto.CheckOutAlquilerDTO;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.PagoService;
import inge2.com.alquileresMaria.service.builder.MpPreferenceBuilder;
import inge2.com.alquileresMaria.service.validators.AuthHelperService;
import inge2.com.alquileresMaria.service.validators.CheckOutHelperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckOutAlquilerService extends AbstractCheckOutService {

    private final PagoService pagoService;
    private final AlquilerService alquilerService;
    @Autowired
    public CheckOutAlquilerService(CheckOutHelperService checkOutHelper, MpPreferenceBuilder mpPreferenceBuilder, AuthHelperService authHelperService, PagoService pagoService, AlquilerService alquilerService) {
        super(checkOutHelper, mpPreferenceBuilder, authHelperService);
        this.pagoService = pagoService;
        this.alquilerService = alquilerService;
    }

    @Override
    public void procesarPagoAprobado(Payment payment) {
        this.pagoService.registrarCobro(payment);
    }

    @Transactional
    public String registrarAlquiler(CheckOutAlquilerDTO checkOutAlquilerDTO, String mail) throws MPException, MPApiException {
        Alquiler alquiler = this.alquilerService.crearAlquiler(checkOutAlquilerDTO.getAlquilerDTO(),mail);

        Preference preference = this.getMpPreferenceBuilder().crearPreferenceAlquiler(alquiler,checkOutAlquilerDTO.getDatosPagoDTO());

        return pagoService.crearPago(preference,alquiler).getInitPoint();//url de pago generada a partir de los datos obtenidos
    }
}

package inge2.com.alquileresMaria.service.checkOut;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.dto.CheckOutAlquilerDTO;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.service.PagoService;
import inge2.com.alquileresMaria.service.builder.MpPreferenceBuilder;
import inge2.com.alquileresMaria.service.helper.AuthHelperService;
import inge2.com.alquileresMaria.service.helper.CheckOutHelperService;
import inge2.com.alquileresMaria.service.useCase.Alquiler.CrearAlquilerUseCase;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CheckOutAlquilerService extends AbstractCheckOutService {

    private final PagoService pagoService;
    private final CrearAlquilerUseCase crearAlquilerUseCase;

    public CheckOutAlquilerService(CheckOutHelperService checkOutHelper, MpPreferenceBuilder mpPreferenceBuilder, AuthHelperService authHelperService, PagoService pagoService, CrearAlquilerUseCase crearAlquilerUseCase) {
        super(checkOutHelper, mpPreferenceBuilder, authHelperService);
        this.pagoService = pagoService;
        this.crearAlquilerUseCase = crearAlquilerUseCase;
    }

    @Override
    public void procesarPagoAprobado(Payment payment) {
        this.pagoService.registrarCobro(payment);
    }

    @Transactional
    public String registrarAlquiler(CheckOutAlquilerDTO checkOutAlquilerDTO, String mail) {
        Alquiler alquiler = this.crearAlquilerUseCase.crearAlquiler(checkOutAlquilerDTO.getAlquilerDTO(),mail);

        Preference preference = this.getMpPreferenceBuilder().crearPreferenceAlquiler(alquiler,checkOutAlquilerDTO.getDatosPagoDTO());

        return pagoService.crearPago(preference,alquiler).getInitPoint();//url de pago generada a partir de los datos obtenidos
    }
}

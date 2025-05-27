package inge2.com.alquileresMaria.service;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.dto.AlquilerDTOCrear;
import inge2.com.alquileresMaria.dto.CheckOutDTO;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.model.Pago;
import inge2.com.alquileresMaria.service.builder.MpPreferenceBuilder;
import inge2.com.alquileresMaria.model.Alquiler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckOutService {
    @Autowired
    private PagoService pagoService;
    @Autowired
    private AutoService autoService;
    @Autowired
    private AlquilerService alquilerService;
    @Autowired
    private MpPreferenceBuilder mpPreferenceBuilder;

    @Transactional
    public String createPreference(CheckOutDTO checkOutDTO) throws MPException, MPApiException {
        DatosPagoDTO datosPagoDTO = checkOutDTO.getDatosPagoDTO();
        AlquilerDTOCrear alquilerDTO = checkOutDTO.getAlquilerDTO();

        Alquiler alquiler = this.alquilerService.crearAlquiler(alquilerDTO);
        PreferenceClient client = new PreferenceClient();

        Preference preference = client
                .create(this.mpPreferenceBuilder.
                        crearPreferenceRequest(alquiler, datosPagoDTO)
                );

        Pago pago = pagoService.crearPago(preference,alquiler);

        return pago.getInitPoint(); //url de pago de prueba generada a partir de los datos obtenidos
    }

    public void procesarNotificacionPago(String dataId) throws MPException, MPApiException {
        //Obtener los datos del pago a partir del id
        PaymentClient paymentClient = new PaymentClient();
        Payment payment = paymentClient.get(Long.parseLong(dataId)); //La api de mercadoPago devuelve el payment si es que existe uno con ese id

        String status = payment.getStatus();
        //Si el estado es aprobado se cambia el estado del pago a PAGADO
        if(status.equals("approved")) {
            String alquilerId = payment.getExternalReference();
            pagoService.registrarCobro(Long.parseLong(alquilerId));
        }
    }
}

package inge2.com.alquileresMaria.service;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.dto.AlquilerDTOCrear;
import inge2.com.alquileresMaria.dto.CheckOutAlquilerDTO;
import inge2.com.alquileresMaria.dto.CheckOutMultaDTO;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.Pago;
import inge2.com.alquileresMaria.service.builder.MpPreferenceBuilder;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.service.helper.ClienteHelperService;
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
    @Autowired
    private ClienteHelperService clienteHelperService;
    @Autowired
    private ClienteService clienteService;

    @Transactional
    public String registrarAlquiler(CheckOutAlquilerDTO checkOutAlquilerDTO) throws MPException, MPApiException {
        DatosPagoDTO datosPagoDTO = checkOutAlquilerDTO.getDatosPagoDTO();
        AlquilerDTOCrear alquilerDTO = checkOutAlquilerDTO.getAlquilerDTO();

        Alquiler alquiler = this.alquilerService.crearAlquiler(alquilerDTO);

        Preference preference = this.mpPreferenceBuilder.crearPreferenceAlquiler(alquiler,datosPagoDTO);

        Pago pago = pagoService.crearPago(preference,alquiler);

        return pago.getInitPoint(); //url de pago generada a partir de los datos obtenidos
    }

    public void procesarNotificacionPagoAlquiler(String dataId) throws MPException, MPApiException {
        Payment payment = this.getPayment(dataId);

        //Si el estado es aprobado se cambia el estado del pago a PAGADO
        if(this.checkStatusApproved(payment)) {
            String alquilerId = payment.getExternalReference();
            pagoService.registrarCobro(Long.parseLong(alquilerId));
        }
    }

    public void procesarNotificacionPagoMulta(String dataId) throws MPException, MPApiException {
        Payment payment = this.getPayment(dataId);

        //Si el estado es aprobado se cambia el estado del pago a PAGADO
        if(this.checkStatusApproved(payment)) {
            String clienteId = payment.getExternalReference();
            this.clienteService.registrarPagoMulta(clienteId);
        }
    }

    public String pagarMulta(CheckOutMultaDTO checkOutMultaDTO) throws MPException, MPApiException {
        Cliente cliente = this.clienteHelperService.findClienteByEmail(checkOutMultaDTO.getMail());

        return this.mpPreferenceBuilder.crearPreferenceMulta(cliente, checkOutMultaDTO.getDatosPagoDTO()).getInitPoint();
    }

    private Payment getPayment(String dataId) throws MPException, MPApiException {
        PaymentClient paymentClient = new PaymentClient();
        return paymentClient.get(Long.parseLong(dataId)); //La api de mercadoPago devuelve el payment si es que existe uno con ese id
    }

    private boolean checkStatusApproved(Payment payment){
        return payment.getStatus().equals("approved");
    }
}

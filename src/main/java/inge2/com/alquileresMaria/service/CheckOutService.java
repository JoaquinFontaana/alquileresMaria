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
import inge2.com.alquileresMaria.service.helper.CheckOutHelperService;
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
    @Autowired
    private CheckOutHelperService checkOutHelperService;

    @Transactional
    public String registrarAlquiler(CheckOutAlquilerDTO checkOutAlquilerDTO) throws MPException, MPApiException {
        DatosPagoDTO datosPagoDTO = checkOutAlquilerDTO.getDatosPagoDTO();
        AlquilerDTOCrear alquilerDTO = checkOutAlquilerDTO.getAlquilerDTO();

        Alquiler alquiler = this.alquilerService.crearAlquiler(alquilerDTO);

        Pago pago = pagoService.crearPago(this.mpPreferenceBuilder.crearPreferenceAlquiler(alquiler,datosPagoDTO),alquiler);
        return pago.getInitPoint(); //url de pago generada a partir de los datos obtenidos
    }
    public String pagarMulta(CheckOutMultaDTO checkOutMultaDTO) throws MPException, MPApiException {
        Cliente cliente = this.clienteHelperService.findClienteByEmail(checkOutMultaDTO.getMail());
        return this.mpPreferenceBuilder.crearPreferenceMulta(cliente, checkOutMultaDTO.getDatosPagoDTO()).getInitPoint();
    }

    @Transactional
    public void procesarNotificacionPagoAlquiler(String dataId) throws MPException, MPApiException {
        Payment payment = this.checkOutHelperService.getPayment(dataId);

        //Si el estado es approved se cambia el estado del pago a PAGADO
        if(this.checkOutHelperService.checkStatusApproved(payment)) {
            String alquilerId = payment.getExternalReference();
            pagoService.registrarCobro(alquilerId);
        }
    }
    @Transactional
    public void procesarNotificacionPagoMulta(String dataId) throws MPException, MPApiException {
        Payment payment = this.checkOutHelperService.getPayment(dataId);

        //Si el estado es approved se pone la multa en 0
        if(this.checkOutHelperService.checkStatusApproved(payment)) {
            String clienteId = payment.getExternalReference();
            this.clienteService.registrarPagoMulta(clienteId);
        }
    }

}

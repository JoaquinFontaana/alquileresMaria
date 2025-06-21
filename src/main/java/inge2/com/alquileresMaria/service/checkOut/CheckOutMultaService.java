package inge2.com.alquileresMaria.service.checkOut;

import com.mercadopago.resources.payment.Payment;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.service.ClienteService;
import inge2.com.alquileresMaria.service.builder.MpPreferenceBuilder;
import inge2.com.alquileresMaria.service.validators.AuthHelperService;
import inge2.com.alquileresMaria.service.validators.CheckOutHelperService;
import inge2.com.alquileresMaria.service.validators.ClienteHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckOutMultaService extends AbstractCheckOutService {

    private final ClienteHelperService clienteHelperService;
    private final ClienteService clienteService;
    @Autowired
    public CheckOutMultaService(CheckOutHelperService checkOutHelper, MpPreferenceBuilder mpPreferenceBuilder, AuthHelperService authHelperService, ClienteHelperService clienteHelperService, ClienteService clienteService) {
        super(checkOutHelper, mpPreferenceBuilder, authHelperService);
        this.clienteHelperService = clienteHelperService;
        this.clienteService = clienteService;
    }

    public String pagarMulta(DatosPagoDTO datosPagoDTO) {
        Cliente cliente = this.clienteHelperService.findClienteByEmail(this.getAuthHelperService().getMailOfContext());
        if(cliente.getMontoMulta() == 0){
            throw new IllegalStateException("El monto de la multa es 0, no hay multa a pagar");
        }
        return this.getMpPreferenceBuilder().crearPreferenceMulta(cliente, datosPagoDTO).getInitPoint();
    }

    @Override
    public void procesarPagoAprobado(Payment payment) {
        this.clienteService.registrarPagoMulta(payment.getExternalReference());
    }
}

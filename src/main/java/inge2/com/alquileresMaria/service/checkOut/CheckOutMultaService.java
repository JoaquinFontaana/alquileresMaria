package inge2.com.alquileresMaria.service.checkOut;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.service.ClienteService;
import inge2.com.alquileresMaria.service.helper.AuthHelperService;
import inge2.com.alquileresMaria.service.helper.ClienteHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckOutMultaService extends AbstractCheckOutService {
    @Autowired
    private ClienteHelperService clienteHelperService;
    @Autowired
    private ClienteService clienteService;


    public String pagarMulta(DatosPagoDTO datosPagoDTO) throws MPException, MPApiException {
        Cliente cliente = this.clienteHelperService.findClienteByEmail(this.getAuthHelperService().getMailOfContext());
        return this.getMpPreferenceBuilder().crearPreferenceMulta(cliente, datosPagoDTO).getInitPoint();
    }

    @Override
    public void procesarPagoAprobado(String externalReference) {
        this.clienteService.registrarPagoMulta(externalReference);
    }
}

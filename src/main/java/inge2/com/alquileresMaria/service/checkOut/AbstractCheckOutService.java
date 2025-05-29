package inge2.com.alquileresMaria.service.checkOut;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import inge2.com.alquileresMaria.service.builder.MpPreferenceBuilder;
import inge2.com.alquileresMaria.service.helper.CheckOutHelperService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter @Setter
public abstract class AbstractCheckOutService {
    @Autowired
    private CheckOutHelperService checkOutHelper;
    @Autowired
    private MpPreferenceBuilder mpPreferenceBuilder;

    public void procesarNotificacion(String dataId,String type) throws MPException, MPApiException {
        if (!"payment".equals(type)){
            return;
        }
        Payment payment = this.checkOutHelper.getPayment(dataId);
        //Si el estado es approved se cambia el estado o valor
        if(this.checkOutHelper.checkStatusApproved(payment)) {
            this.procesarPagoAprobado(payment.getExternalReference());
        }
    }
    public abstract void procesarPagoAprobado(String externalReference);
}

package inge2.com.alquileresMaria.service.helper;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.stereotype.Service;

@Service
public class CheckOutHelperService {
    public Payment getPayment(String dataId) throws MPException, MPApiException {
        PaymentClient paymentClient = new PaymentClient();
        return paymentClient.get(Long.parseLong(dataId)); //La api de mercadoPago devuelve el payment si es que existe uno con ese id
    }

    public boolean checkStatusApproved(Payment payment){
        return payment.getStatus().equals("approved");
    }
}

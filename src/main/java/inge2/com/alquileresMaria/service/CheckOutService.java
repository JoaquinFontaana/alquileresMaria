package inge2.com.alquileresMaria.service;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceBackUrls;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckOutService {

    public String createPreference(/*Datos que mande el front sobre el pago a crear*/DatosPagoDTO datosPagoDTO) throws MPException, MPApiException {

        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title(datosPagoDTO.getTitulo())
                .quantity(1)
                .currencyId("ARS")
                .unitPrice(new BigDecimal(datosPagoDTO.getTotal()))
                .build();

        List<PreferenceItemRequest> items = List.of(item);
        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success(datosPagoDTO.getSuccesUrl()) //Rutas de redirrecion en el frontend
                .failure(datosPagoDTO.getFailureUrl())
                .pending(datosPagoDTO.getPendingUrl())
                .build();
        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrlsRequest)
                .autoReturn("approved") //Investigar
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(request);

        return preference.getInitPoint();//url de pago generada a partir de los datos obtenidos
    }

}

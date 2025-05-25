package inge2.com.alquileresMaria.helpers;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;

import java.math.BigDecimal;
import java.util.List;

public class MpPreferenceBuilder {
    private PreferenceItemRequest crearPreferenceItemRequest(DatosPagoDTO datosPagoDTO, double total){
        return PreferenceItemRequest.builder()
                .title(datosPagoDTO.getTitulo())
                .quantity(1)
                .currencyId("ARS")
                .unitPrice(new BigDecimal(total))
                .build();
    }

    private PreferenceBackUrlsRequest crearPreferenceBackUrlsRequest(DatosPagoDTO datosPagoDTO){
        return  PreferenceBackUrlsRequest.builder()
                .success(datosPagoDTO.getSuccessUrl()) //Rutas de redirrecion en el frontend
                .failure(datosPagoDTO.getFailureUrl())
                .pending(datosPagoDTO.getPendingUrl())
                .build();
    }

    public PreferenceRequest crearPreferenceRequest(String externalReference, DatosPagoDTO datosPagoDTO, double total){
        PreferenceItemRequest item = this.crearPreferenceItemRequest(datosPagoDTO,total);
        PreferenceBackUrlsRequest backUrlsRequest = this.crearPreferenceBackUrlsRequest(datosPagoDTO);
        return  PreferenceRequest.builder()
                .items(List.of(item))
                .backUrls(backUrlsRequest)
                //Para implementar las notificaciones la API debe estar en un dominio accesible en internet
                .notificationUrl("miDominio/checkOut/webhook")
                .externalReference(externalReference)
                .autoReturn("approved") //Investigar
                .build();
    }
}

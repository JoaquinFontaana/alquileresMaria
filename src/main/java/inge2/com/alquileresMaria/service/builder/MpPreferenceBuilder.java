package inge2.com.alquileresMaria.service.builder;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.model.Alquiler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class MpPreferenceBuilder {
    @Value("${NGROK_URL}")
    private String publicUrl;

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

    public PreferenceRequest crearPreferenceRequest(Alquiler alquiler, DatosPagoDTO datosPagoDTO){
        PreferenceItemRequest item = this.crearPreferenceItemRequest(datosPagoDTO,alquiler.calcularTotal());
        PreferenceBackUrlsRequest backUrlsRequest = this.crearPreferenceBackUrlsRequest(datosPagoDTO);
        return  PreferenceRequest.builder()
                .items(List.of(item))
                .backUrls(backUrlsRequest)
                //Para implementar las notificaciones la API debe estar en un dominio accesible en internet
                .notificationUrl(publicUrl + "/checkOut/webhook")
                //Referencia a la tabla Pago guardada en la BD (id del alquiler)
                .externalReference(alquiler.getId().toString())
                .autoReturn("approved") //Investigar
                .build();
    }
}

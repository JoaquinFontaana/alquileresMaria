package inge2.com.alquileresMaria.service;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.dto.AlquilerDTOCrear;
import inge2.com.alquileresMaria.dto.CheckOutDTO;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Pago;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckOutService {
    @Autowired
    private PagoService pagoService;
    @Autowired
    private AutoService autoService;
    @Autowired
    private AlquilerService alquilerService;
    @Autowired
    private  PreferenceClient client;

    @Transactional
    public String createPreference(CheckOutDTO checkOutDTO) throws MPException, MPApiException {

        DatosPagoDTO datosPagoDTO = checkOutDTO.getDatosPagoDTO();
        AlquilerDTOCrear alquilerDTO = checkOutDTO.getAlquilerDTO();


        Alquiler alquiler = this.alquilerService.crearAlquiler(alquilerDTO);
        double total = alquiler.calcularTotal();

        String idAlquiler = alquiler.getId().toString();
        PreferenceRequest request = this.crearPreferenceRequest(idAlquiler,datosPagoDTO,total);

        Preference preference = this.client.create(request);

        pagoService.crearPago(preference.getId(),alquiler,preference.getSandboxInitPoint(),total);

        return preference.getSandboxInitPoint(); //url de pago generada a partir de los datos obtenidos
    }

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

    private PreferenceRequest crearPreferenceRequest(String externalReference, DatosPagoDTO datosPagoDTO,double total){
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

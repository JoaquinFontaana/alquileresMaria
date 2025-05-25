package inge2.com.alquileresMaria.service;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.dto.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.CheckOutDTO;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Pago;
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

    public String createPreference(/*Datos que mande el front sobre el pago a crear*/CheckOutDTO checkOutDTO) throws MPException, MPApiException {
        DatosPagoDTO datosPagoDTO = checkOutDTO.getDatosPagoDTO();
        AlquilerDTOListar alquilerDTO = checkOutDTO.getAlquilerDTO();

        Auto auto = autoService.findAutoByPatente(datosPagoDTO.getPatente());
        Double total = datosPagoDTO.calcularTotal(auto.getPrecioPorDia());
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title(datosPagoDTO.getTitulo())
                .quantity(1)
                .currencyId("ARS")
                .unitPrice(new BigDecimal(total))
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
                .externalReference()
                .autoReturn("approved") //Investigar
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(request);
        Alquiler
        Pago pago = new Pago(preference.getId(),,preference.getInitPoint(),total);


        return preference.getInitPoint();//url de pago generada a partir de los datos obtenidos
    }

}

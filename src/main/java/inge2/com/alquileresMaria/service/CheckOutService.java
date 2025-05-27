package inge2.com.alquileresMaria.service;

import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.dto.AlquilerDTOCrear;
import inge2.com.alquileresMaria.dto.CheckOutDTO;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.service.builder.MpPreferenceBuilder;
import inge2.com.alquileresMaria.model.Alquiler;
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

    @Transactional
    public String createPreference(CheckOutDTO checkOutDTO) throws MPException, MPApiException {
        DatosPagoDTO datosPagoDTO = checkOutDTO.getDatosPagoDTO();
        AlquilerDTOCrear alquilerDTO = checkOutDTO.getAlquilerDTO();

        Alquiler alquiler = this.alquilerService.crearAlquiler(alquilerDTO);
        PreferenceClient client = new PreferenceClient();
        Preference preference = client
                .create(this.mpPreferenceBuilder
                        .crearPreferenceRequest(
                                alquiler.getId().toString(),
                                datosPagoDTO,
                                alquiler.calcularTotal()
                        )
                );

        pagoService.crearPago(preference,alquiler);

        return preference.getSandboxInitPoint(); //url de pago de prueba generada a partir de los datos obtenidos
    }

}

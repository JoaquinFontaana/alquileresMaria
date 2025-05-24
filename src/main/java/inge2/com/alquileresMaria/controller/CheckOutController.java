package inge2.com.alquileresMaria.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkOut")
public class CheckOutController {

    @Autowired
    private CheckOutService checkOutService;
    @PostMapping("/createPreference")
    public String createPreference(@RequestBody DatosPagoDTO datosPagoDTO) throws MPException, MPApiException {
        String initPoint = this.checkOutService.createPreference(datosPagoDTO);
        return initPoint; //Url de redireccion del pago
    }
}

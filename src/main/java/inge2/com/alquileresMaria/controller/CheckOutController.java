package inge2.com.alquileresMaria.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import inge2.com.alquileresMaria.dto.CheckOutDTO;
import inge2.com.alquileresMaria.service.CheckOutService;
import inge2.com.alquileresMaria.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkOut")
public class CheckOutController {

    @Autowired
    private CheckOutService checkOutService;
    @Autowired
    private PagoService pagoService;

    /*
    ** Ejemplo de peticion
    *  {
    "datosPagoDTO": {
      "titulo": "Reserva de Auto - 5 días",
      "successUrl": "https://miapp.com/pago/exito",
      "failureUrl": "https://miapp.com/pago/fallo",
      "pendingUrl": "https://miapp.com/pago/pendiente"
    },
    "alquilerDTO": {
      "rangoFecha": {
        "fechaDesde": "2025-06-10",
        "fechaHasta": "2025-06-15"
      },
      "licenciaConductor": "ABC123456",
      "clienteMail": "maria.gonzalez@example.com",
      "patenteAuto": "ADF111",
      "sucursalEntrega": "Trelew",
      "sucursalDevolucion": "Trelew"
    }
 }   */
    @PostMapping("/createPreference")
    public String createPreference(@Valid @RequestBody CheckOutDTO checkOutDTO) throws MPException, MPApiException {
        return this.checkOutService.createPreference(checkOutDTO); //Url de redireccion del pago
    }

    //Aca se recibiran las notificaciones referidas a las preference de mercadopago
    @PostMapping("/webhook")
    public ResponseEntity<String> recibirNotificacion(@RequestParam("type") String type, @RequestParam("data.id") String dataId) throws MPException, MPApiException {
        // Solo procesamos pagos
        if ("payment".equals(type)) {
            checkOutService.procesarNotificacionPago(dataId);
        }
        // Responder 200 OK para que MP no reintente
        return ResponseEntity.ok("OK");
    }
}

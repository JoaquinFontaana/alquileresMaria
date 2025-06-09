package inge2.com.alquileresMaria.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import inge2.com.alquileresMaria.dto.CheckOutAlquilerDTO;
import inge2.com.alquileresMaria.dto.DatosPagoDTO;
import inge2.com.alquileresMaria.service.PagoService;
import inge2.com.alquileresMaria.service.checkOut.CheckOutAlquilerService;
import inge2.com.alquileresMaria.service.checkOut.CheckOutMultaService;
import inge2.com.alquileresMaria.service.helper.AuthHelperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkOut")
public class CheckOutController {

    private final CheckOutAlquilerService checkOutAlquilerService;
    private final CheckOutMultaService checkOutMultaService;
    private final AuthHelperService authHelperService;
    private PagoService pagoService;
    @Autowired
    public CheckOutController(CheckOutAlquilerService checkOutAlquilerService, CheckOutMultaService checkOutMultaService, AuthHelperService authHelperService, PagoService pagoService) {
        this.checkOutAlquilerService = checkOutAlquilerService;
        this.checkOutMultaService = checkOutMultaService;
        this.authHelperService = authHelperService;
        this.pagoService = pagoService;
    }

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
          "patenteAuto": "ADF111",
          "sucursal": "Trelew",
        }
     }   */
    @PostMapping("/cliente/registrarAlquiler")
    public String registrarAlquilerCliente(@Valid @RequestBody CheckOutAlquilerDTO checkOutAlquilerDTO) throws MPException, MPApiException {
        return this.checkOutAlquilerService.registrarAlquiler(checkOutAlquilerDTO,this.authHelperService.getMailOfContext()); //Url de redireccion del pago
    }

    @PostMapping("/cliente/pagarMulta")
    public String pagarMulta(@Valid @RequestBody DatosPagoDTO datosPagoDTO) throws MPException, MPApiException {
        return this.checkOutMultaService.pagarMulta(datosPagoDTO);
    }

    /*
    @PostMapping("/empleado/registrarAlquiler")
    public String registrarAlquilerEmpleado(@Valid @RequestBody CheckOutAlquilerDTO checkOutAlquilerDTO) throws MPException, MPApiException {
        return this.checkOutAlquilerService.registrarAlquiler(checkOutAlquilerDTO,mail);
    }
    */
    //Aca se recibiran las notificaciones de mercadopago referidas a los alquileres
    @PostMapping("/notificacion/alquiler")
    public ResponseEntity<String> recibirNotificacionAlquiler(@RequestParam("type") String type, @RequestParam("data.id") String dataId) throws MPException, MPApiException {
        checkOutAlquilerService.procesarNotificacion(dataId,type);
        // Responder 200 OK para que MP no reintente
        return ResponseEntity.ok("OK");
    }

    //Aca se recibiran las notificaciones de mercadopago referidas a las multas
    @PostMapping("/notificacion/multa")
    public ResponseEntity<String> recibirNotificacionMulta(@RequestParam("type") String type, @RequestParam("data.id") String dataId) throws MPException, MPApiException {
            checkOutMultaService.procesarNotificacion(dataId,type);
        // Responder 200 OK para que MP no reintente
        return ResponseEntity.ok("OK");
    }
    @GetMapping("/eliminar")
    public void eliminar(){
        this.pagoService.deletePagosPendientes();
    }


}

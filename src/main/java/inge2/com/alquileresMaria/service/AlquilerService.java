package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.AlquilerDTOCrear;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import inge2.com.alquileresMaria.service.helper.AlquilerHelperService;
import inge2.com.alquileresMaria.service.helper.ClienteHelperService;
import inge2.com.alquileresMaria.service.helper.AutoHelperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlquilerService {

    @Autowired
    private IAlquilerRepository repository;
    @Autowired
    private EmailService serviceEmail;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private AutoHelperService autoHelperService;
    @Autowired
    private ClienteHelperService clienteHelperService;
    @Autowired
    private AlquilerHelperService alquilerHelperService;
    @Transactional
    public Alquiler crearAlquiler(AlquilerDTOCrear alquilerDTO){
        Auto auto = this.autoHelperService.findAutoByPatente(alquilerDTO.getPatenteAuto());
        this.autoHelperService.verificarDisponibilidad(auto,alquilerDTO.getRangoFecha());

        Sucursal entregaSucursal = this.sucursalService.findSucursalByCiudad(alquilerDTO.getSucursalEntrega());
        Sucursal devolucionSucursal = this.sucursalService.findSucursalByCiudad(alquilerDTO.getSucursalDevolucion());

        Cliente cliente = this.clienteHelperService.findClienteByEmail(alquilerDTO.getClienteMail());
        Alquiler alquiler = new Alquiler(alquilerDTO,auto,cliente,devolucionSucursal,entregaSucursal);

        return this.repository.save(alquiler);
    }

    @Transactional
    public void cancelarReservas(List<Alquiler> alquileres){
        //Solo mandar mails a clientes con reservas futuras verificar que la fecha no sea menor a la actual
        List<Alquiler> alquileresPosteriores = this.alquilerHelperService.filtrarAlquileresPosteriores(alquileres);
        this.repository.deleteAllById(this.alquilerHelperService.obtenerIdsDeAlquileres(alquileresPosteriores));

        String subject = "Su auto reservado ya no se encuentra disponible";
        String body = "Ofrecer opcion de rembolso o cambiar de auto";

        this.serviceEmail.sendEmailsClientes(this.alquilerHelperService.obtenerClientesDeAlquileres(alquileresPosteriores), subject,body);
    }


}

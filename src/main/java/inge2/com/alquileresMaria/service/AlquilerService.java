package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.AlquilerDTOCrear;
import inge2.com.alquileresMaria.dto.AlquilerDTOListar;
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
import java.util.stream.Collectors;

@Service
public class AlquilerService {

    private final IAlquilerRepository repository;
    private final EmailService serviceEmail;
    private final SucursalService sucursalService;
    private final AutoHelperService autoHelperService;
    private final ClienteHelperService clienteHelperService;
    private final AlquilerHelperService alquilerHelperService;

    public AlquilerService(IAlquilerRepository repository, EmailService serviceEmail, SucursalService sucursalService, AutoHelperService autoHelperService, ClienteHelperService clienteHelperService, AlquilerHelperService alquilerHelperService) {
        this.repository = repository;
        this.serviceEmail = serviceEmail;
        this.sucursalService = sucursalService;
        this.autoHelperService = autoHelperService;
        this.clienteHelperService = clienteHelperService;
        this.alquilerHelperService = alquilerHelperService;
    }

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

    public List<AlquilerDTOListar> obtenerAlquileres() {
        return this.repository.findAll()
                .stream()
                .map(AlquilerDTOListar::new)
                .collect(Collectors.toList());
    }


}

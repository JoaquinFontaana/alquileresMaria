package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.AlquilerDTOCrear;
import inge2.com.alquileresMaria.dto.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.ReservaDTOCancelar;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import inge2.com.alquileresMaria.service.helper.AlquilerHelperService;
import inge2.com.alquileresMaria.service.helper.ClienteHelperService;
import inge2.com.alquileresMaria.service.helper.AutoHelperService;
import jakarta.persistence.EntityNotFoundException;
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
    public Alquiler crearAlquiler(AlquilerDTOCrear alquilerDTO,String mail){
        this.alquilerHelperService.checkDuracionAlquiler(alquilerDTO.getRangoFecha());
        this.alquilerHelperService.checkDisponibilidadConductor(alquilerDTO.getRangoFecha(),alquilerDTO.getLicenciaConductor());

        Auto auto = this.autoHelperService.findAutoByPatente(alquilerDTO.getPatenteAuto());
        this.autoHelperService.verificarDisponibilidad(auto,alquilerDTO.getRangoFecha());

        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(alquilerDTO.getSucursal());

        Cliente cliente = this.clienteHelperService.findClienteByEmail(mail);
        Alquiler alquiler = new Alquiler(alquilerDTO,auto,cliente,sucursal);

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
    @Transactional
    public void eliminarAlquileresVencidos(List<Alquiler> alquileres){
        this.repository.deleteAll(alquileres);
    }
    public List<AlquilerDTOListar> obtenerAlquileres() {
        return this.repository.findAll()
                .stream()
                .map(AlquilerDTOListar::new)
                .collect(Collectors.toList());
    }

    public void cancelarReserva(ReservaDTOCancelar reservaDTO){
        Alquiler reserva = repository.findAlquilerByLicenciaConductorAndRangoFecha(reservaDTO.getLicenciaCliente(), reservaDTO.getFechaDesde(),
                                reservaDTO.getFechaFin())
                .orElseThrow(() -> new EntityNotFoundException("No existe una reserva para este cliente, en esta fecha"));
        this.repository.deleteById(reserva.getId());
    }

}

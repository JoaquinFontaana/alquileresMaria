package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOCrear;
import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOFilter;
import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.alquiler.ReservaDTOCancelar;
import inge2.com.alquileresMaria.model.*;
import inge2.com.alquileresMaria.model.enums.EstadoAlquiler;
import inge2.com.alquileresMaria.model.enums.EstadoPago;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import inge2.com.alquileresMaria.service.builder.AlquilerFilterBuilder;
import inge2.com.alquileresMaria.service.filter.alquiler.AlquilerFilterComponent;
import inge2.com.alquileresMaria.service.validators.AlquilerHelperService;
import inge2.com.alquileresMaria.service.validators.ClienteHelperService;
import inge2.com.alquileresMaria.service.validators.AutoHelperService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AlquilerService {

    private final IAlquilerRepository repository;

    private final SucursalService sucursalService;
    private final AutoHelperService autoHelperService;
    private final ClienteHelperService clienteHelperService;
    private final AlquilerHelperService alquilerHelperService;
    private final RembolsoService rembolsoService;
    private final AlquilerFilterBuilder filterBuilder;

    public AlquilerService(IAlquilerRepository repository, SucursalService sucursalService, AutoHelperService autoHelperService, ClienteHelperService clienteHelperService, AlquilerHelperService alquilerHelperService, RembolsoService rembolsoService, AlquilerFilterBuilder filterBuilder) {
        this.repository = repository;
        this.sucursalService = sucursalService;
        this.autoHelperService = autoHelperService;
        this.clienteHelperService = clienteHelperService;
        this.alquilerHelperService = alquilerHelperService;
        this.rembolsoService = rembolsoService;
        this.filterBuilder = filterBuilder;
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
        this.alquilerHelperService.rembolsarReservasPagadas(alquileresPosteriores
                .stream()
                .filter(a -> a.getPago().getEstadoPago().equals(EstadoPago.PAGADO))
                .toList()
        );
        this.alquilerHelperService.eliminarAlquileres(
                alquileresPosteriores
                        .stream()
                        .filter(a -> a.getPago().getEstadoPago().equals(EstadoPago.PENDIENTE))
                        .toList()
        );
    }

    public List<AlquilerDTOListar> listarAlquileres(AlquilerDTOFilter filtros) {
        AlquilerFilterComponent filter = filterBuilder.buildFilter(filtros);
        return filter
                .getAlquileres()
                .stream()
                .map( alquiler -> new AlquilerDTOListar(alquiler))
                .toList();
    }

    @Transactional
    public void cancelarReserva(ReservaDTOCancelar reservaDTO){
        Alquiler reserva = this.alquilerHelperService.findByConductorRangoFechas(reservaDTO);

        this.alquilerHelperService.checkForCancelacion(reserva);
        if(reserva.getPago().getEstadoPago() == EstadoPago.PENDIENTE){
            this.repository.delete(reserva);
        }
        else {
            this.rembolsoService.crearRembolso(reserva);
            reserva.setEstadoAlquiler(EstadoAlquiler.CANCELADO);
            this.repository.save(reserva);
        }
    }

}

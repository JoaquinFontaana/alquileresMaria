package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.alquiler.*;
import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
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

import java.time.LocalDate;
import java.util.List;


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
    public void cancelarReserva(AlquilerDTOFechaLicencia reservaDTO){
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
    @Transactional
    public void iniciarAlquiler(AlquilerDTOFechaLicencia reservaDTO){
        Alquiler reserva = this.alquilerHelperService.findByConductorRangoFechas(reservaDTO);
        this.alquilerHelperService.checkAvailable(reserva);
        this.autoHelperService.checkAutoDisponible(reserva.getAuto());
        reserva.iniciar();
        this.repository.save(reserva);
    }
    @Transactional
    public void finalizarAlquilerCorrecto(AlquilerDTOFechaLicencia reservaDTO) {
        Alquiler reserva = this.alquilerHelperService.findByConductorRangoFechas(reservaDTO);
        reserva.finalizar();
        this.repository.save(reserva);
    }
    @Transactional
    public void finalizarAlquilerMantenimiento(MultaAlquilerDTO multaAlquilerDTO) {
        Alquiler reserva = this.alquilerHelperService.findByConductorRangoFechas(multaAlquilerDTO);
        this.cancelarReservas(reserva.getAuto().getReservas());
        reserva.finalizarConMantenimiento(multaAlquilerDTO.getMontoMulta());
        this.repository.save(reserva);
    }

    public List<AlquilerDTOListar> listarPendientes(String sucursal) {
        Sucursal ciudad = this.sucursalService.findSucursalByCiudad(sucursal);
        return this.repository.findBySucursal_Ciudad(ciudad.getCiudad()).stream()
                .filter(a -> a.estaDisponibleRetiro())
                .map(a -> new AlquilerDTOListar(a))
                .toList();
    }

    public List<AutoDTOListar> sugerirSimilares(String licenciaConductor, LocalDate fechaDesde, LocalDate fechaHasta) {
        Alquiler alquiler = this.alquilerHelperService.findByConductorRangoFechas(new AlquilerDTOFechaLicencia(fechaDesde, fechaHasta,licenciaConductor));
        this.alquilerHelperService.checkFechaReserva(alquiler);
        this.autoHelperService.checkAutoNoDisponible(alquiler.getAuto());
        return this.autoHelperService.findSimilaresPorPrecioOCategoria(alquiler.getAuto())
                .stream()
                .filter(auto -> auto.disponibleEnRangoFechas(alquiler.getRangoFecha()))
                .map(AutoDTOListar::new)
                .toList();
    }

    public void cambiarAuto(AlquilerDTOCambiarAuto alquilerDTOCambiarAuto){
        Auto auto = this.autoHelperService.findAutoByPatente(alquilerDTOCambiarAuto.getPatenteAutoNuevo());
        List<String> patentes = this.sugerirSimilares(alquilerDTOCambiarAuto.getLicencia(),alquilerDTOCambiarAuto.getFechaDesde(),alquilerDTOCambiarAuto.getFechaFin())
                .stream()
                .map(AutoDTOListar::getPatente)
                .toList();
        if(!patentes.contains(auto.getPatente())){
            throw new IllegalArgumentException("El auto seleccionado para el cambio no es similar al auto actual");
        }
        Alquiler alquiler = this.alquilerHelperService.findByConductorRangoFechas(alquilerDTOCambiarAuto);
        alquiler.setAuto(auto);
        this.repository.save(alquiler);
    }

/*
    @Transactional
    public void agregarExtra(List<Extra> extras, ReservaDTOFechaLicencia reserva){
        Alquiler alquiler = this.alquilerHelperService.findByConductorRangoFechas(reserva);
        alquiler.addExtras(extras);
        this.repository.save(alquiler);
    }

 */

}

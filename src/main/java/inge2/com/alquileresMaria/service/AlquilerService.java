package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.alquiler.*;
import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.model.*;
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


@Service
public class AlquilerService {

    private final IAlquilerRepository repository;

    private final SucursalService sucursalService;
    private final AutoHelperService autoHelperService;
    private final ClienteHelperService clienteHelperService;
    private final AlquilerHelperService alquilerHelperService;
    private final RembolsoService rembolsoService;
    private final AlquilerFilterBuilder filterBuilder;
    private final EmailService emailService;
    private final AutoService autoService;

    public AlquilerService(IAlquilerRepository repository, SucursalService sucursalService, AutoHelperService autoHelperService, ClienteHelperService clienteHelperService, AlquilerHelperService alquilerHelperService, RembolsoService rembolsoService, AlquilerFilterBuilder filterBuilder, EmailService emailService, AutoService autoService) {
        this.repository = repository;
        this.sucursalService = sucursalService;
        this.autoHelperService = autoHelperService;
        this.clienteHelperService = clienteHelperService;
        this.alquilerHelperService = alquilerHelperService;
        this.rembolsoService = rembolsoService;
        this.filterBuilder = filterBuilder;
        this.emailService = emailService;
        this.autoService = autoService;
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

    public List<AlquilerDTOListar> listarAlquileres(AlquilerDTOFilter filtros) {
        AlquilerFilterComponent filter = filterBuilder.buildFilter(filtros);
        return filter
                .getAlquileres()
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }

    @Transactional
    public void cancelarReserva(Long codigoAlquiler) {
        this.alquilerHelperService.findById(codigoAlquiler).cancelar(this);
    }

    public void sendEmailBajaAuto(Alquiler alquiler,String body) {
        String subject = "Su auto reservado "+ alquiler.getAuto().getModelo() + " ya no se encuentra disponible";
        this.emailService.sendEmail(alquiler.getCliente().getMail(), subject, body);
    }

    public void rembolsarAlquiler(Alquiler alquiler,double montoRembolso){
        this.rembolsoService.crearRembolso(alquiler, montoRembolso);
    }

    public void eliminarAlquiler(Alquiler alquiler){
        this.repository.delete(alquiler);
    }

    public void saveAlquiler(Alquiler alquiler){
        this.repository.save(alquiler);
    }

    @Transactional
    public void iniciarAlquiler(Long codigoAlquiler) {
        this.alquilerHelperService.findById(codigoAlquiler).iniciar(this,autoService);
    }

    @Transactional
    public void finalizarAlquilerCorrecto(Long codigoAlquiler) {
        this.alquilerHelperService.findById(codigoAlquiler).finalizar(autoService,this);
    }

    @Transactional
    public void finalizarAlquilerMantenimiento(MultaAlquilerDTO multaAlquilerDTO) {
        this.alquilerHelperService.findById(multaAlquilerDTO.getCodigoAlquiler())
                .finalizarConMantenimiento(this,autoService,multaAlquilerDTO.getMontoMulta());
    }

    public List<AlquilerDTOListar> listarPendientesEntrega(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        return this.repository.findRetiroPendienteByCiudad(ciudad)
                .stream()
                .filter(Alquiler::retiroDisponible)
                .map(AlquilerDTOListar::new)
                .toList();
    }

    public List<AutoDTOListar> sugerirSimilares(Long codigoAlquiler) {
        Alquiler alquiler = this.alquilerHelperService.findById(codigoAlquiler);
        this.autoHelperService.checkAutoNoDisponible(alquiler.getAuto());
        return this.autoHelperService.findSimilaresPorPrecioOCategoria(alquiler.getAuto())
                .stream()
                .filter(auto -> auto.disponibleEnRangoFechas(alquiler.getRangoFecha()))
                .map(AutoDTOListar::new)
                .toList();
    }

    public void cambiarAuto(AlquilerDTOCambiarAuto alquilerDTOCambiarAuto){
        Auto auto = this.autoHelperService.findAutoByPatente(alquilerDTOCambiarAuto.getPatenteAutoNuevo());

        List<String> patentes = this.sugerirSimilares(alquilerDTOCambiarAuto.getCodigoAlquiler())
                .stream()
                .map(AutoDTOListar::getPatente)
                .toList();

        if(!patentes.contains(auto.getPatente())){
            throw new IllegalArgumentException("El auto seleccionado para el cambio no es similar al auto actual");
        }

        Alquiler alquiler = this.alquilerHelperService.findById(alquilerDTOCambiarAuto.getCodigoAlquiler());
        alquiler.setAuto(auto);
        this.repository.save(alquiler);
    }

    public List<AlquilerDTOListar> listarPendientesDevolucion(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        return this.repository.findEnUsoByCiudad(ciudad)
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }


}

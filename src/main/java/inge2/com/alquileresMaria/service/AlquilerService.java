package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.alquiler.*;
import inge2.com.alquileresMaria.model.*;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import inge2.com.alquileresMaria.service.builder.AlquilerFilterBuilder;
import inge2.com.alquileresMaria.service.filter.alquiler.AlquilerFilterComponent;
import inge2.com.alquileresMaria.service.validators.AlquilerHelperService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlquilerService {

    private final IAlquilerRepository repository;

    private final SucursalService sucursalService;
    private final AlquilerHelperService alquilerHelperService;
    private final RembolsoService rembolsoService;
    private final AlquilerFilterBuilder filterBuilder;
    private final EmailService emailService;

    public AlquilerService(IAlquilerRepository repository, SucursalService sucursalService, AlquilerHelperService alquilerHelperService, RembolsoService rembolsoService, AlquilerFilterBuilder filterBuilder, EmailService emailService) {
        this.repository = repository;
        this.sucursalService = sucursalService;
        this.alquilerHelperService = alquilerHelperService;
        this.rembolsoService = rembolsoService;
        this.filterBuilder = filterBuilder;
        this.emailService = emailService;
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

    @Transactional
    public void rembolsarAlquiler(Alquiler alquiler,double montoRembolso){
        this.rembolsoService.crearRembolso(alquiler, montoRembolso);
    }
    @Transactional
    public void eliminarAlquiler(Alquiler alquiler){
        this.repository.delete(alquiler);
    }
    @Transactional
    public Alquiler saveAlquiler(Alquiler alquiler){
        return this.repository.save(alquiler);
    }


    public List<AlquilerDTOListar> listarPendientesEntrega(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        return this.repository.findRetiroPendienteByCiudad(ciudad)
                .stream()
                .filter(Alquiler::retiroDisponible)
                .map(AlquilerDTOListar::new)
                .toList();
    }


    public List<AlquilerDTOListar> listarPendientesDevolucion(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        return this.repository.findEnUsoByCiudad(ciudad)
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }


}

package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.AutoDTO;
import inge2.com.alquileresMaria.dto.AutoFilterDTO;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.EstadoAuto;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import inge2.com.alquileresMaria.repository.ISucursalRepository;
import inge2.com.alquileresMaria.service.Filter.BaseAutoFilter;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutoService {
    @Autowired
    private IAutoRepository autoRepository;
    @Autowired
    private BaseAutoFilter serviceFilter;
    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private AlquilerService serviceAlquiler;
    @Transactional
    public void crearAuto(AutoDTO autoDto){
        this.checkPatenteNotExists(autoDto.getPatente());
        Sucursal sucursal = this.sucursalService.findSucursalCiudad(autoDto.getSucursal());
        Auto auto = new Auto(autoDto,sucursal);
        autoRepository.save(auto);
    }

    public List<AutoDTO> listarAutos(AutoFilterDTO opcionesFiltrado){
        return opcionesFiltrado
                .buildFilter(this.serviceFilter)
                .listar()
                .stream()
                .map(auto -> new AutoDTO(auto))
                .toList();
    }
    @Transactional
    public void eliminarAuto(String patente){
        Auto auto = this.findAutoPatente(patente);
        this.checkAutoNotAlquilado(auto);
        List<Alquiler> alquileres = auto.getReservas();
        serviceAlquiler.cancelarReservas(alquileres);
        auto.setEstado(EstadoAuto.BAJA);
        this.autoRepository.save(auto);
    }

    public void actualizarAuto(AutoDTO autoActualizado){
        Sucursal sucursal = this.sucursalService.findSucursalCiudad(autoActualizado.getSucursal());
        Auto auto = this.findAutoPatente(autoActualizado.getPatente());
        auto.actualizarAuto(autoActualizado,sucursal);
        this.autoRepository.save(auto);
    }

    //Helpers para validaciones o buscar registros en la BD
    private void checkPatenteNotExists(String patente) {
        if (autoRepository.existsByPatente(patente)) {
            throw new EntityExistsException("La patente " + patente + " ya se encuentra registrada");
        }
    }

    private Auto findAutoPatente(String patente) {
        return autoRepository.findByPatente(patente)
                .orElseThrow(() -> new EntityNotFoundException("La patente " + patente + " no existe"));
    }

    private void checkAutoNotAlquilado(Auto auto) {
        if (auto.getEstado() == EstadoAuto.ALQUILADO) {
            throw new IllegalStateException("El vehiculo con patente " + auto.getPatente() + " esta alquilado en este momento");
        }
    }
}

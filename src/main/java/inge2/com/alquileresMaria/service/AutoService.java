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
    private IAutoRepository repository;
    @Autowired
    private BaseAutoFilter serviceFilter;
    @Autowired
    private ISucursalRepository sucursalRepository;

    @Autowired
    private AlquilerService serviceAlquiler;
    @Transactional
    public void crearAuto(AutoDTO autoDto){
        if(this.repository.existsByPatente(autoDto.getPatente())){
            throw new EntityExistsException("La patente " +autoDto.getPatente() + " ya se encuentra registrada");
        }
        Sucursal sucursal = this.sucursalRepository.findByCiudad(autoDto.getSucursal())
                .orElseThrow(() -> new EntityNotFoundException("La sucursal con ciudad " + autoDto.getSucursal() + " no existe"));
        Auto auto = new Auto(autoDto,sucursal);
        repository.save(auto);
    }

    public List<AutoDTO> listarAutos(AutoFilterDTO opcionesFiltrado){
        return opcionesFiltrado.buildFilter(this.serviceFilter).listar()
                .stream().map(auto -> new AutoDTO(auto))
                .toList();
    }
    @Transactional
    public void eliminarAuto(String patente){
        if(!this.repository.existsByPatente(patente)){
            throw new EntityExistsException("La patente " +patente + " no se encuentra registrada");
        }
        Auto auto = this.repository.findByPatente(patente)
                .orElseThrow(()->new EntityNotFoundException("la patente " + patente + " no existe"));
        if(auto.getEstado().equals(EstadoAuto.ALQUILADO)){
            throw new RuntimeException("el vehiculo con patente " + patente + " esta alquilado en este momento");
        }

        List<Alquiler> alquileres = auto.getReservas();
        serviceAlquiler.cancelarReservas(alquileres);

        auto.setEstado(EstadoAuto.BAJA);
        this.repository.save(auto);
    }

}

package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.AutoDtoListar;
import inge2.com.alquileresMaria.dto.AutoDTOCrear;
import inge2.com.alquileresMaria.dto.AutoFilterDTO;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import inge2.com.alquileresMaria.repository.ISucursalRepository;
import inge2.com.alquileresMaria.service.Filter.BaseAutoFilter;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {
    @Autowired
    private IAutoRepository repository;
    @Autowired
    private BaseAutoFilter serviceFilter;
    @Autowired
    private ISucursalRepository sucursalRepository;

    public void crearAuto(AutoDTOCrear autoDto){
        if(this.repository.existsByPatente(autoDto.getPatente())){
            throw new EntityExistsException("La patente " +autoDto.getPatente() + " ya se encuentra registrada");
        }
        Sucursal sucursal = this.sucursalRepository.findByCiudad(autoDto.getSucursal())
                .orElseThrow(() -> new EntityNotFoundException("La sucursal con ciudad " + autoDto.getSucursal() + " no existe"));
        Auto auto = new Auto(autoDto,sucursal);
        repository.save(auto);
    }

    public List<AutoDtoListar> listarAutos(AutoFilterDTO opcionesFiltrado){
        return opcionesFiltrado.buildFilter(this.serviceFilter).listar()
                .stream().map(auto -> new AutoDtoListar(auto))
                .toList();
    }
}

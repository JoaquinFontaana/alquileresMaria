package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.AutoDTO;
import inge2.com.alquileresMaria.dto.AutoFilterDTO;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.enums.EstadoAuto;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import inge2.com.alquileresMaria.service.builder.FilterBuilder;
import inge2.com.alquileresMaria.service.filter.BaseAutoFilter;
import inge2.com.alquileresMaria.service.filter.IAutoFilter;
import inge2.com.alquileresMaria.service.helper.AutoHelperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {
    @Autowired
    private IAutoRepository autoRepository;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private AutoHelperService autoHelperService;
    @Autowired
    private AlquilerService serviceAlquiler;
    @Autowired
    private FilterBuilder filterBuilder;

    @Transactional
    public void crearAuto(AutoDTO autoDto){
        this.autoHelperService.checkPatenteNotExists(autoDto.getPatente());

        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(autoDto.getSucursal());
        Auto auto = new Auto(autoDto,sucursal);

        autoRepository.save(auto);
    }

    public List<AutoDTO> listarAutos(AutoFilterDTO opcionesFiltrado){
        IAutoFilter filter = this.filterBuilder.buildFilter(opcionesFiltrado);
        return filter
                .listar()
                .stream()
                .map(auto -> new AutoDTO(auto))
                .toList();
    }
    @Transactional
    public void eliminarAuto(String patente){
        Auto auto = this.autoHelperService.findAutoByPatente(patente);
        this.autoHelperService.checkAutoNotAlquilado(auto);

        serviceAlquiler.cancelarReservas(auto.getReservas());
        auto.setEstado(EstadoAuto.BAJA);

        this.autoRepository.save(auto);
    }
    @Transactional
    public void actualizarAuto(AutoDTO autoActualizado){
        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(autoActualizado.getSucursal());
        Auto auto = this.autoHelperService.findAutoByPatente(autoActualizado.getPatente());

        auto.actualizarAuto(autoActualizado,sucursal);
        this.autoRepository.save(auto);
    }
}

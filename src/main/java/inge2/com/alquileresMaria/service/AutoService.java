package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.AutoDTO;
import inge2.com.alquileresMaria.dto.AutoFilterDTO;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.EstadoAuto;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import inge2.com.alquileresMaria.service.Filter.BaseAutoFilter;
import inge2.com.alquileresMaria.service.Verfication.VerficacionAutoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {
    @Autowired
    private IAutoRepository autoRepository;
    @Autowired
    private BaseAutoFilter serviceFilter;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private VerficacionAutoService verficacionAutoService;
    @Autowired
    private AlquilerService serviceAlquiler;

    @Transactional
    public void crearAuto(AutoDTO autoDto){
        this.verficacionAutoService.checkPatenteNotExists(autoDto.getPatente());
        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(autoDto.getSucursal());
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
        Auto auto = this.verficacionAutoService.findAutoByPatente(patente);
        this.verficacionAutoService.checkAutoNotAlquilado(auto);
        serviceAlquiler.cancelarReservas(auto.getReservas());
        auto.setEstado(EstadoAuto.BAJA);
        this.autoRepository.save(auto);
    }
    @Transactional
    public void actualizarAuto(AutoDTO autoActualizado){
        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(autoActualizado.getSucursal());
        Auto auto = this.verficacionAutoService.findAutoByPatente(autoActualizado.getPatente());
        auto.actualizarAuto(autoActualizado,sucursal);
        this.autoRepository.save(auto);
    }
}

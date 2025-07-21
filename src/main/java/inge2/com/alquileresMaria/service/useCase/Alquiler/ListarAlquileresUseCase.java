package inge2.com.alquileresMaria.service.useCase.Alquiler;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOFilter;
import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.SucursalService;
import inge2.com.alquileresMaria.service.builder.AlquilerFilterBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarAlquileresUseCase {
    private final AlquilerFilterBuilder filterBuilder;
    private final SucursalService sucursalService;
    private final AlquilerService alquilerService;

    public ListarAlquileresUseCase(AlquilerFilterBuilder filterBuilder, SucursalService sucursalService, AlquilerService alquilerService) {
        this.filterBuilder = filterBuilder;
        this.sucursalService = sucursalService;
        this.alquilerService = alquilerService;
    }

    public List<AlquilerDTOListar> listarAlquileres(AlquilerDTOFilter filtros) {
        return filterBuilder.buildFilter(filtros)
                .getAlquileres()
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }
    
    public List<AlquilerDTOListar> listarPendientesEntrega(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        return this.alquilerService.findRetiroPendienteByCiudad(ciudad)
                .stream()
                .filter(Alquiler::retiroDisponible)
                .map(AlquilerDTOListar::new)
                .toList();
    }


    public List<AlquilerDTOListar> listarPendientesDevolucion(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        return this.alquilerService.findEnUsoByCiudad(ciudad)
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }
}

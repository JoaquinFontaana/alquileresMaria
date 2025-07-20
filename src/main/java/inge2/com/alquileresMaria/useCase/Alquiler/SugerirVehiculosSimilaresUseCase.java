package inge2.com.alquileresMaria.useCase.Alquiler;

import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.service.validators.AlquilerHelperService;
import inge2.com.alquileresMaria.service.validators.AutoHelperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SugerirVehiculosSimilaresUseCase {
    private final AlquilerHelperService alquilerHelperService;
    private final AutoHelperService autoHelperService;

    public SugerirVehiculosSimilaresUseCase(AlquilerHelperService alquilerHelperService, AutoHelperService autoHelperService) {
        this.alquilerHelperService = alquilerHelperService;
        this.autoHelperService = autoHelperService;
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
}

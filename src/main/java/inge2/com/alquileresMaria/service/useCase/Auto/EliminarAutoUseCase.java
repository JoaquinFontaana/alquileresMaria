package inge2.com.alquileresMaria.service.useCase.Auto;

import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.AutoService;
import inge2.com.alquileresMaria.service.helper.AutoHelperService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EliminarAutoUseCase {
    private final AutoHelperService autoHelperService;
    private final AlquilerService alquilerService;
    private final AutoService autoService;

    public EliminarAutoUseCase(AutoHelperService autoHelperService, AlquilerService alquilerService, AutoService autoService) {
        this.autoHelperService = autoHelperService;
        this.alquilerService = alquilerService;
        this.autoService = autoService;
    }

    @Transactional
    public void eliminarAuto(String patente){
        this.autoHelperService.findAutoByPatente(patente).darDeBaja(alquilerService,autoService);
    }

}

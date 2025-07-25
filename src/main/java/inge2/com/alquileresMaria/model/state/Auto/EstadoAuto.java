package inge2.com.alquileresMaria.model.state.Auto;

import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.enums.EstadoAutoEnum;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.AutoService;

public interface EstadoAuto {
    EstadoAutoEnum getEstadoAutoEnum();

    void iniciarAlquiler(Auto auto, AutoService autoService);

    void finalizarAlquiler(Auto auto,AutoService autoService);

    void darDeBaja(Auto auto, AlquilerService alquilerService, AutoService autoService);

    //Retiro disponible
    boolean estaDisponible();
    //El auto se encuentra en condiciones de ser alquilado
    boolean esAlquilable();

    void finalizarMantenimiento(Auto auto, AutoService autoService);

    void iniciarMantenimiento(Auto auto, AutoService autoService,AlquilerService alquilerService);
}

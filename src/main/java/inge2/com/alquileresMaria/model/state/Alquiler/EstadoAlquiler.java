package inge2.com.alquileresMaria.model.state.Alquiler;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.AutoService;

public interface EstadoAlquiler {
    EstadoAlquilerEnum getEstadoAlquilerEnum();

    void finalizar(AlquilerService alquilerService, AutoService autoService, Alquiler alquiler);

    void finalizarConMantenimiento(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService, int multa);

    void finalizarVencido(Alquiler alquiler, AlquilerService alquilerService);

    boolean retiroDisponible(Alquiler alquiler);

    void cancelar(Alquiler alquiler, AlquilerService alquilerService);

    void iniciar(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService);

    void bajaAuto(Alquiler alquiler, AlquilerService alquilerService);

    void procresarPago(Alquiler alquiler,AlquilerService alquilerService);
}

package inge2.com.alquileresMaria.model.state.Alquiler;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.AutoService;

public class Finalizado implements EstadoAlquiler{

    @Override
    public void cancelar(Alquiler alquiler, AlquilerService alquilerService) {
        throw new IllegalStateException("El alquiler ya se encuentra finalizado, no se puede cancelar.");
    }

    @Override
    public boolean retiroDisponible(Alquiler alquiler) {
        return false;
    }

    @Override
    public void iniciar(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService) {
        throw new IllegalStateException("El alquiler ya se encuentra finalizado, no se puede iniciar nuevamente.");
    }

    @Override
    public void bajaAuto(Alquiler alquiler, AlquilerService alquilerService) {

    }

    @Override
    public void finalizarConMantenimiento(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService, int multa) {
        throw new IllegalStateException("El alquiler ya se encuentra finalizado, no se puede finalizar con mantenimiento.");
    }

    @Override
    public void finalizar(AlquilerService alquilerService, AutoService autoService, Alquiler alquiler) {
        throw new IllegalStateException("El alquiler ya se encuentra finalizado, no se puede finalizar nuevamente.");
    }

    @Override
    public EstadoAlquilerEnum getEstadoAlquilerEnum() {
        return EstadoAlquilerEnum.FINALIZADO;
    }
}

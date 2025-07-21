package inge2.com.alquileresMaria.model.state.Alquiler;

import inge2.com.alquileresMaria.model.Alquiler;
import jakarta.persistence.PostLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EstadoAlquilerListener {

    private final EstadoAlquilerFactory factory;

    public EstadoAlquilerListener(EstadoAlquilerFactory factory) {
        this.factory = factory;
    }

    @PostLoad
    public void setState(Alquiler alquiler) {
        alquiler.setState(factory.getEstado(alquiler.getEstadoAlquilerEnum()));
    }
}


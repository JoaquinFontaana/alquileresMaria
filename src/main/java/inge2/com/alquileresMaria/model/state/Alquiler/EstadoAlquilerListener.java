package inge2.com.alquileresMaria.model.state.Alquiler;

import inge2.com.alquileresMaria.model.Alquiler;
import jakarta.persistence.PostLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EstadoAlquilerListener {

    private static EstadoAlquilerFactory factory;

    @Autowired
    public void setFactory(EstadoAlquilerFactory factory) {
        EstadoAlquilerListener.factory = factory;
    }

    @PostLoad
    public void setState(Alquiler alquiler) {
        alquiler.setState(factory.getEstado(alquiler.getEstadoAlquilerEnum()));
    }
}


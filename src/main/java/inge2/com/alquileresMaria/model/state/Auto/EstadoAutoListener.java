package inge2.com.alquileresMaria.model.state.Auto;

import inge2.com.alquileresMaria.model.Auto;
import jakarta.persistence.PostLoad;
import org.springframework.stereotype.Component;

@Component
public class EstadoAutoListener {
    private static final EstadoAutoFactory factory = new EstadoAutoFactory();

    @PostLoad
    public static void setState(Auto auto) {
        auto.setState(factory.getEstado(auto.getEstado()));
    }
}

package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Pago {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String preference_id;
    @OneToOne
    @JoinColumn(name = "alquiler_id")
    private Alquiler external_reference;
    private String init_point;
    private Double monto;
    private EstadoPago estadoPago;

    public Pago(String preference_id, Alquiler external_reference, String init_point, Double monto) {
        this.preference_id = preference_id;
        this.external_reference = external_reference;
        this.init_point = init_point;
        this.monto = monto;
        this.estadoPago = EstadoPago.PENDIENTE;
    }
}

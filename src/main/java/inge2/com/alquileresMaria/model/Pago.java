package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String preferenceId;
    @OneToOne
    @JoinColumn(name = "alquiler_id")
    private Alquiler externalReference;
    private String initPoint;
    private Double monto;
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;

    public Pago(String preference_id, Alquiler external_reference, String init_point, Double monto) {
        this.preferenceId = preference_id;
        this.externalReference = external_reference;
        this.initPoint = init_point;
        this.monto = monto;
        this.estadoPago = EstadoPago.PENDIENTE;
    }

    public Pago(){

    }
}

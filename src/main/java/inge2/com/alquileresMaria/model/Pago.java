package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.model.enums.EstadoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

@Entity @Getter @Setter
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String preferenceId;
    @OneToOne(optional = false)
    @JoinColumn(name = "alquiler_id")
    private Alquiler alquiler;
    @NotBlank
    private String initPoint;
    @Positive(message = "El monto del pago debe ser positivo") @NotNull
    private Double monto;
    @Enumerated(EnumType.STRING) @NotNull
    private EstadoPago estadoPago;
    @NotNull
    private LocalDateTime fechaCreacion;
    @NotNull
    private LocalDateTime fechaExpiracion;

    public Pago(String preference_id, Alquiler alquiler, String init_point, Double monto) {
        this.preferenceId = preference_id;
        this.alquiler = alquiler;
        this.initPoint = init_point;
        this.monto = monto;
        this.estadoPago = EstadoPago.PENDIENTE;
        this.fechaCreacion = LocalDateTime.now();
        //10 minutos tiempo de expiracion
        this.fechaExpiracion = this.fechaCreacion.plusMinutes(10);
    }
    public Pago(){

    }
    public boolean estaExpirado() {
        return LocalDateTime.now().isAfter(this.fechaExpiracion);
    }
}

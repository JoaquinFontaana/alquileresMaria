package inge2.com.alquileresMaria.model;

import com.mercadopago.resources.preference.Preference;
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
import java.time.OffsetDateTime;
import java.time.temporal.TemporalUnit;

@Entity @Getter @Setter
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private OffsetDateTime fechaCreacion;
    @NotNull
    private OffsetDateTime fechaExpiracion;

    public Pago(Preference preference,Alquiler alquiler) {
        this.alquiler = alquiler;
        this.initPoint = preference.getInitPoint();
        this.monto = alquiler.calcularTotal();
        this.estadoPago = EstadoPago.PENDIENTE;
        this.fechaCreacion = preference.getDateCreated();
        this.fechaExpiracion = this.fechaCreacion.plusMinutes(15);
    }
    public Pago(){

    }
}

package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOCrear;
import inge2.com.alquileresMaria.model.enums.EstadoAlquiler;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El rango de fechas es obligatorio")
    @Embedded
    private RangoFecha rangoFecha;
    @NotBlank(message = "La licencia del conductor es obligatoria")
    private String licenciaConductor;
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne(optional = false)
    @JoinColumn(name = "auto_id")
    private Auto auto;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
    @OneToOne(
            mappedBy = "alquiler",
            optional = false,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Pago pago;
    @NotNull @Enumerated(EnumType.STRING)
    private EstadoAlquiler estadoAlquiler;

    public boolean sinSolapamiento(RangoFecha rango){
        return this.rangoFecha.sinSolapamiento(rango);
    }

    public double calcularTotal(){
        return this.auto.getPrecioPorDia() * this.rangoFecha.cantidadDeDias();
    }

    public double calcularRembolso(){
        return this.auto.getRembolso().calcularRembolso(this.calcularTotal());
    }

    public Alquiler (AlquilerDTOCrear alquilerDTOCrear,Auto auto,Cliente cliente,Sucursal sucursal){
        this.auto = auto;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.licenciaConductor = alquilerDTOCrear.getLicenciaConductor();
        this.rangoFecha = alquilerDTOCrear.getRangoFecha();
        this.estadoAlquiler = EstadoAlquiler.CONFIRMACION_PENDIENTE;
    }
    public Alquiler(){

    }
}

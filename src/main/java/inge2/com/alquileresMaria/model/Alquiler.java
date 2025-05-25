package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.AlquilerDTOCrear;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity @Getter @Setter
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private RangoFecha rangoFecha;
    @NotBlank(message = "La licencia del conductor es obligatoria")
    private String licenciaConductor;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;
    @ManyToOne
    @JoinColumn(name = "devolucion_sucursal_id")
    private Sucursal devolucionEnSucursal;
    @ManyToOne
    @JoinColumn(name = "entrega_sucursal_id")
    private Sucursal entregaEnSucursal;
    @OneToOne(mappedBy = "alquiler")
    private Pago pago;

    public boolean disponibleEnRangoFechas(RangoFecha rango){
        return this.rangoFecha.sinSolapamiento(rango);
    }
    public Alquiler (AlquilerDTOCrear alquilerDTOCrear,Auto auto,Cliente cliente,Sucursal devolucionEnSucursal,Sucursal entregaEnSucursal){
        this.auto = auto;
        this.cliente = cliente;
        this.devolucionEnSucursal = devolucionEnSucursal;
        this.entregaEnSucursal = entregaEnSucursal;
        this.licenciaConductor = alquilerDTOCrear.getLicenciaConductor();
        this.rangoFecha = alquilerDTOCrear.getRangoFecha();
    }
    //Calcular total
}

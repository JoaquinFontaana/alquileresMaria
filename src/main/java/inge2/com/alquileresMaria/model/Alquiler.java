package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.AlquilerDTOCrear;
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
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
    @OneToOne(mappedBy = "alquiler")
    private Pago pago;

    public boolean disponibleEnRangoFechas(RangoFecha rango){
        return this.rangoFecha.sinSolapamiento(rango);
    }

    public double calcularTotal(){
        return this.auto.getPrecioPorDia() * this.rangoFecha.cantidadDeDias();
    }
    public Alquiler (AlquilerDTOCrear alquilerDTOCrear,Auto auto,Cliente cliente,Sucursal sucursal){
        this.auto = auto;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.licenciaConductor = alquilerDTOCrear.getLicenciaConductor();
        this.rangoFecha = alquilerDTOCrear.getRangoFecha();
    }
    public Alquiler(){

    }
    //Calcular total
}

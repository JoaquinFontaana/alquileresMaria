package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOCrear;
import inge2.com.alquileresMaria.model.enums.EstadoAlquiler;
import inge2.com.alquileresMaria.model.enums.Extra;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @OneToOne(mappedBy = "alquilerRembolsado")
    private Rembolso rembolso;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "alquiler_extras", joinColumns = @JoinColumn(name = "alquiler_id"))
    @Column(name = "extra")
    private List<Extra> extras;

    public boolean sinSolapamiento(RangoFecha rango){
        return this.rangoFecha.sinSolapamiento(rango);
    }

    public double calcularTotal(){
        return (this.auto.getPrecioPorDia() * this.rangoFecha.cantidadDeDias()) + this.calcularExtras();
    }

    public double calcularExtras(){return this.extras.stream().mapToDouble(Extra::getPrecio).sum();}

    public double calcularRembolso(){
        return this.auto.getRembolso().calcularRembolso(this.calcularTotal());
    }

    public boolean esPosterior(LocalDate fecha){
        return this.rangoFecha.esPosterior(fecha);
    }

    public void addExtras(List<Extra> extras){
        extras.stream()
                .filter(e -> !this.extras.contains(e))
                .forEach(e -> this.extras.add(e));
    }

    public Alquiler (AlquilerDTOCrear alquilerDTOCrear,Auto auto,Cliente cliente,Sucursal sucursal){
        this.auto = auto;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.licenciaConductor = alquilerDTOCrear.getLicenciaConductor();
        this.rangoFecha = alquilerDTOCrear.getRangoFecha();
        this.estadoAlquiler = EstadoAlquiler.CONFIRMACION_PENDIENTE;
        this.rembolso = null;
        this.extras = new ArrayList<>();
        this.addExtras(alquilerDTOCrear.getExtras());
    }

    public Alquiler(){

    }

    public boolean isToday() {
        return this.getRangoFecha().getFechaDesde().isEqual(LocalDate.now());
    }

    public void iniciar() {
        this.setEstadoAlquiler(EstadoAlquiler.EN_USO);
    }

    public void setClienteMulta(int montoMulta) {
        this.cliente.setMontoMulta(montoMulta);
    }

    public void finalizar() {
        this.setEstadoAlquiler(EstadoAlquiler.FINALIZADO);
    }
}

package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.AutoDtoListar;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter @Setter
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La patente es obligatoria")
    private String patente;
    @Positive(message = "La capacidad debe ser mayor a cero")
    private int capacidad;
    @NotBlank(message = "La marca es obligatoria")
    private String marca;
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;
    @Positive(message = "El precio por dia debe ser positivo")
    private double precioPorDia;
    //ToDO Poner un atributo imagen
    @Enumerated(EnumType.STRING)
    private CategoriaAuto categoria;
    @Enumerated(EnumType.STRING)
    private Rembolso rembolso;
    @Enumerated(EnumType.STRING)
    private EstadoAuto estado;
    @OneToMany(mappedBy = "auto")
    private List<Alquiler> reservas;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    public Auto(AutoDtoListar dto, Sucursal sucursal) {
        this.patente = dto.getPatente();
        this.capacidad = dto.getCapacidad();
        this.marca = dto.getMarca();
        this.modelo = dto.getModelo();
        this.precioPorDia = dto.getPrecioPorDia();
        this.categoria = dto.getCategoria();
        this.rembolso = dto.getRembolso();
        this.estado = dto.getEstado();
        this.sucursal = sucursal;
    }
}

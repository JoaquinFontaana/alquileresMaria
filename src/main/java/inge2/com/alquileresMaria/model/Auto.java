package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
@Entity
public class Auto {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "La patente es obligatoria")
    private String patente;
    @NotBlank(message = "La capacidad es obligatoria")
    private int capacidad;
    @NotBlank(message = "La marca es obligatoria")
    private String marca;
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;
    @NotBlank(message = "El precio por dia es obligatorio")
    private double precioPorDia;
    //Poner un atributo imagen
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

    public String getPatente() {
        return patente;
    }
}

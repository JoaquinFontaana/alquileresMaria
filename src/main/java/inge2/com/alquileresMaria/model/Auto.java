package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Auto {
    @Id
    @GeneratedValue
    private Long id;
    private String patente;
    private int capacidad;
    private CategoriaAuto categoria;
    private String marca;
    private String modelo;
    private double precioPorDia;
    //Poner un atributo imagen
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

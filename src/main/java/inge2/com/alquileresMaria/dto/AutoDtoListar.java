package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.internal.ImmutableEntityEntry;

import java.util.List;
@Getter @Setter
public class AutoDtoListar {
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
    @NotBlank(message = "La sucursal es obligatoria")
    private String sucursal;

    public AutoDtoListar(Auto auto) {
        this.patente = auto.getPatente();
        this.capacidad = auto.getCapacidad();
        this.marca = auto.getMarca();
        this.modelo = auto.getModelo();
        this.precioPorDia = auto.getPrecioPorDia();
        this.categoria = auto.getCategoria();
        this.rembolso = auto.getRembolso();
        this.estado = auto.getEstado();
        this.sucursal = auto.getSucursal().getCiudad();
    }
}

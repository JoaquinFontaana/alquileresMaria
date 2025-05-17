package inge2.com.alquileresMaria.model;

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
    private LocalDate inicio;
    @NotNull
    private LocalDate fin;
    private double total;
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
    //Cuando se crea el alquiler se debe verificar la edad del conductor aunque no se guarde >= 18


}

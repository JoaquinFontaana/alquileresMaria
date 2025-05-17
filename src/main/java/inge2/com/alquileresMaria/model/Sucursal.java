package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Entity
@Getter @Setter
public class Sucursal {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;
    @OneToMany(mappedBy = "sucursal")
    private List<Auto> autos;

}

package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.dto.user.PersonaDTOPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Setter
public class Cliente extends Persona {

    @OneToMany(mappedBy = "cliente")
    private List<Alquiler> alquileres;
    @Min(value = 0)
    private double montoMulta;

    public Cliente() {
        super();
        this.alquileres = new ArrayList<>();
        this.montoMulta = 0;
    }

    public Cliente (PersonaDTOPassword dto, Rol rol){
        super(dto,rol);
        this.alquileres = new ArrayList<>();
        this.montoMulta = 0;
    }
    public Cliente (PersonaDTO dto, Rol rol, String password){
        super(dto,rol,password);
        this.alquileres = new ArrayList<>();
        this.montoMulta = 0;
    }
}

package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.PersonaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Setter
public class Cliente extends Persona {

    @OneToMany(mappedBy = "cliente")
    private List<Alquiler> alquileres;

    public Cliente (PersonaDTO dto,Rol rol){
        super(dto,rol);
        this.alquileres = new ArrayList<>();
    }
}

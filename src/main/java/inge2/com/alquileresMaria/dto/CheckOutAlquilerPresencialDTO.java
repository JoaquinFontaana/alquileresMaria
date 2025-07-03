package inge2.com.alquileresMaria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CheckOutAlquilerPresencialDTO extends CheckOutAlquilerDTO{
    @NotBlank(message = "El mail del cliente es obligatorio")
    private String mailCliente;

    public CheckOutAlquilerPresencialDTO() {
        super();
    }
}

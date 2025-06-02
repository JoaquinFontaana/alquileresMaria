package inge2.com.alquileresMaria.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class LoginDTO {

    @Email(message = "Ingresa un email valido")
    private String mail;
    @Email(message = "Ingresa un email valido")
    private String password;

    public LoginDTO(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }
}

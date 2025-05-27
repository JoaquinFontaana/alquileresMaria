package inge2.com.alquileresMaria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DatosPagoDTO {
    @NotBlank
    private String titulo;
    @NotBlank
    private String successUrl;
    @NotBlank
    private String failureUrl;
    @NotBlank
    private String pendingUrl;

}

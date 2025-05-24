package inge2.com.alquileresMaria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DatosPagoDTO {
    private String titulo;
    private Double total;
    private String succesUrl;
    private String failureUrl;
    private String pendingUrl;
}

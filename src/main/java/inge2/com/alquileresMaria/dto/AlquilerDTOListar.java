package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter

public class AlquilerDTOListar {
  //  @NotBlank(message)
    private long id;
    private LocalDate inicio;
    private LocalDate fin;
    private double total;
    private String licenciaConductor;
    private String clienteMail;
    private AutoDTO auto;

    private String sucursalEntrega;
    private String sucursalDevolucion;

    public AlquilerDTOListar(Alquiler alquiler){
        this.id = alquiler.getId();
        this.inicio = alquiler.getRangoFecha().getFechaDesde();
        this.fin = alquiler.getRangoFecha().getFechaHasta();
        this.licenciaConductor = alquiler.getLicenciaConductor();
        this.clienteMail = alquiler.getCliente().getMail();
        this.auto = new AutoDTO(alquiler.getAuto());
        this.sucursalDevolucion = alquiler.getDevolucionEnSucursal().getCiudad();
        this.sucursalEntrega = alquiler.getEntregaEnSucursal().getCiudad();
    }
}

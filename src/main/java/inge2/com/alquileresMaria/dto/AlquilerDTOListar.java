package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.*;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class AlquilerDTOListar {
    @Embedded
    private RangoFecha rangoFecha;
    private String licenciaConductor;
    private String clienteMail;
    private AutoDTO auto;
    private String sucursalEntrega;
    private String sucursalDevolucion;

    public AlquilerDTOListar(Alquiler alquiler){
        this.rangoFecha = alquiler.getRangoFecha();
        this.licenciaConductor = alquiler.getLicenciaConductor();
        this.clienteMail = alquiler.getCliente().getMail();
        this.auto = new AutoDTO(alquiler.getAuto());
        this.sucursalDevolucion = alquiler.getDevolucionEnSucursal().getCiudad();
        this.sucursalEntrega = alquiler.getEntregaEnSucursal().getCiudad();
    }
    public AlquilerDTOListar(){

    }
}

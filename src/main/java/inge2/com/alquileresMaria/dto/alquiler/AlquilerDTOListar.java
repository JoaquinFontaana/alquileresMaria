package inge2.com.alquileresMaria.dto.alquiler;

import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.model.*;
import inge2.com.alquileresMaria.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileresMaria.model.enums.EstadoPago;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlquilerDTOListar {
    @Embedded
    private RangoFecha rangoFecha;
    private String licenciaConductor;
    private String clienteMail;
    private AutoDTOListar auto;
    private String sucursal;
    private double monto;
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;
    @Enumerated(EnumType.STRING)
    private EstadoAlquilerEnum estadoAlquilerEnum;
    private RembolsoDTO rembolsoDTO;
    private String urlPago;

    public AlquilerDTOListar(Alquiler alquiler){
        this.rangoFecha = alquiler.getRangoFecha();
        this.licenciaConductor = alquiler.getLicenciaConductor();
        this.clienteMail = alquiler.getCliente().getMail();
        this.auto = new AutoDTOListar(alquiler.getAuto());
        this.sucursal = alquiler.getSucursal().getCiudad();
        this.estadoPago = alquiler.getPago().getEstadoPago();
        this.monto = alquiler.getPago().getMonto();
        this.urlPago = alquiler.getPago().getInitPoint();
        this.estadoAlquilerEnum = alquiler.getEstadoAlquilerEnum();
        if(alquiler.getRembolso() != null) {
            this.rembolsoDTO = new RembolsoDTO(alquiler.getRembolso());
        }
    }
    public AlquilerDTOListar(){

    }
}

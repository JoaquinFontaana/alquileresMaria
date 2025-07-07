package inge2.com.alquileresMaria.dto.alquiler;

import inge2.com.alquileresMaria.model.enums.EstadoAlquiler;
import inge2.com.alquileresMaria.model.enums.EstadoPago;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class AlquilerDTOFilter {
    private String nombreSucursal;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private List<EstadoAlquiler> estadoAlquiler;
    private String clienteMail;
    private List<EstadoPago> estadoPago;

    public AlquilerDTOFilter(String nombreSucursal, List<EstadoPago> estadoPago, String clienteMail, LocalDate fechaHasta, List<EstadoAlquiler> estadoAlquiler, LocalDate fechaDesde) {
        this.nombreSucursal = nombreSucursal;
        this.estadoPago = estadoPago;
        this.clienteMail = clienteMail;
        this.fechaHasta = fechaHasta;
        this.estadoAlquiler = estadoAlquiler;
        this.fechaDesde = fechaDesde;
    }
    public AlquilerDTOFilter(){

    }
}

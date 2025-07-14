package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.estadisticas.*;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EstadisticasService {
    @Autowired
    private IAlquilerRepository alquilerRepository;

    // 5. Ingresos en un periodo de tiempo
    public EstadisticaIngresoPeriodoDTO obtenerIngresosPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        Double ingresos = alquilerRepository.findIngresosPeridoTiempo(fechaInicio, fechaFin);

        EstadisticaIngresoPeriodoDTO dto = new EstadisticaIngresoPeriodoDTO();
        dto.setIngresos(ingresos != null ? ingresos : 0.0);
        dto.setFechaInicio(fechaInicio.toString());
        dto.setFechaFin(fechaFin.toString());

        return dto;
    }

    public List<EstadisticaIngresoSucursalCantidadDTO> obtenerIngresosPorSucursal() {
        return alquilerRepository.findIngresosYCantidadPorSucursal()
                .stream()
                .map(obj -> new EstadisticaIngresoSucursalCantidadDTO(
                        (String) obj[0],
                        (Double) obj[1],
                        (Long) obj[2]
                )).toList();
    }

    public List<EstadisticaVehiculoSucursalMontoDTO> obtenerVehiculosPorSucursal() {
        return alquilerRepository.findMasAlquiladosSucursalConMonto()
                .stream()
                .map(obj -> new EstadisticaVehiculoSucursalMontoDTO(
                        (String) obj[0],
                        (Auto) obj[1],
                        (Long) obj[2],
                        (Double) obj[3]
                )).toList();
    }

    public List<EstadisticaVehiculoMontoDTO> obtenerVehiculosMasAlquilados() {
        return alquilerRepository.findVehiculosMasAlquiladosConMonto()
                .stream()
                .map(obj -> new EstadisticaVehiculoMontoDTO(
                        (Auto) obj[0],
                        (Long) obj[1],
                        (Double) obj[2]
                )).toList();
    }

    public List<EstadisticaCategoriaMontoDTO> obtenerCategoriasMasAlquiladas() {
        return alquilerRepository.findCategoriasMasAlquiladasConMonto()
                .stream()
                .map(obj -> new EstadisticaCategoriaMontoDTO(
                        (CategoriaAuto) obj[0],
                        (Long) obj[1],
                        (Double) obj[2]
                )).toList();
    }

    public List<EstadisticaClienteMontoDTO> obtenerTopClientes() {
        return alquilerRepository.findTopClientesConMonto()
                .stream()
                .map(obj -> new EstadisticaClienteMontoDTO(
                        (Cliente) obj[0],
                        (Long) obj[1],
                        (Double) obj[2]
                )).toList();
    }

    public EstadisticaIngresoResumenDTO obtenerResumenIngresos() {
        Object[] result = alquilerRepository.findResumenIngresosTotales();
        return new EstadisticaIngresoResumenDTO(
                (Double) result[0],
                (Long) result[1],
                (Double) result[2],
                (Long) result[3]
        );
    }
}

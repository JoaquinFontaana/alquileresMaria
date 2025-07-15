package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.dto.estadisticas.*;
import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.Empleado;
import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
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
                        new AutoDTOListar((Auto) obj[1]),
                        (Long) obj[2],
                        (Double) obj[3]
                )).toList();
    }

    public List<EstadisticaVehiculoMontoDTO> obtenerVehiculosMasAlquilados() {
        return alquilerRepository.findVehiculosMasAlquiladosConMonto()
                .stream()
                .map(obj -> new EstadisticaVehiculoMontoDTO(
                        new AutoDTOListar((Auto) obj[0]),
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
                        new PersonaDTO((Cliente) obj[0]),
                        (Long) obj[1],
                        (Double) obj[2]
                )).toList();
    }

    public EstadisticaIngresoResumenDTO obtenerResumenIngresos() {
        Object[] result = alquilerRepository.findResumenIngresosTotales();

        BigDecimal montoTotalBD = result[0] != null ? (BigDecimal) result[0] : BigDecimal.ZERO;
        BigInteger cantidadAlquileresBI = result[1] != null ? (BigInteger) result[1] : BigInteger.ZERO;
        BigDecimal montoReembolsadoBD = result[2] != null ? (BigDecimal) result[2] : BigDecimal.ZERO;
        BigInteger cantidadReembolsosBI = result[3] != null ? (BigInteger) result[3] : BigInteger.ZERO;

        return new EstadisticaIngresoResumenDTO(
                montoTotalBD.doubleValue(),
                cantidadAlquileresBI.longValue(),
                montoReembolsadoBD.doubleValue(),
                cantidadReembolsosBI.longValue()
        );
    }
}

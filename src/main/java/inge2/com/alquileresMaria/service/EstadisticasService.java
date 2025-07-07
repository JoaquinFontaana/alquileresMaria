package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.auto.AutoDTO;
import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.dto.estadisticas.*;
import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadisticasService {
    @Autowired
    private IAlquilerRepository alquilerRepository;

    // 1. Vehículos más alquilados por sucursal
    public List<EstadisticaVehiculoSucursalDTO> obtenerVehiculosMasAlquiladosPorSucursalDTO() {
        List<Object[]> resultados = alquilerRepository.findMasAlquiladosSucursal();
        List<EstadisticaVehiculoSucursalDTO> dtos = new ArrayList<>();

        for (Object[] fila : resultados) {
            String sucursal = (String) fila[0];
            Auto auto = (Auto) fila[1];
            Long cantidad = (Long) fila[2];

            AutoDTOListar autoDTO = new AutoDTOListar(auto);

            EstadisticaVehiculoSucursalDTO dto = new EstadisticaVehiculoSucursalDTO();
            dto.setSucursal(sucursal);
            dto.setAuto(autoDTO);
            dto.setCantidad(cantidad);

            dtos.add(dto);
        }

        return dtos;
    }

    // 2. Vehículos más alquilados (general)
    public List<EstadisticaVehiculoDTO> obtenerVehiculosMasAlquiladosDTO() {
        List<Object[]> resultados = alquilerRepository.findVehiculosMasAlquilados();
        List<EstadisticaVehiculoDTO> dtos = new ArrayList<>();

        for (Object[] fila : resultados) {
            Auto auto = (Auto) fila[0];
            Long cantidad = (Long) fila[1];

            AutoDTO autoDTO = new AutoDTOListar(auto);

            EstadisticaVehiculoDTO dto = new EstadisticaVehiculoDTO();
            dto.setAuto(autoDTO);
            dto.setCantidad(cantidad);

            dtos.add(dto);
        }

        return dtos;
    }

    // 3. Categoría más alquilada
    public List<EstadisticaCategoriaDTO> obtenerCategoriasMasAlquiladasDTO() {
        List<Object[]> resultados = alquilerRepository.findCategoriasMasAlquiladas();
        List<EstadisticaCategoriaDTO> dtos = new ArrayList<>();

        for (Object[] fila : resultados) {
            CategoriaAuto categoria = (CategoriaAuto) fila[0];
            Long cantidad = (Long) fila[1];

            EstadisticaCategoriaDTO dto = new EstadisticaCategoriaDTO();
            dto.setCategoria(categoria);
            dto.setCantidad(cantidad);

            dtos.add(dto);
        }

        return dtos;
    }

    // 4. Ingresos por sucursal
    public List<EstadisticaIngresoSucursalDTO> obtenerIngresosPorSucursal() {
        List<Object[]> resultados = alquilerRepository.findIngresosSucursales();

        return resultados.stream()
                .map(obj -> {
                    EstadisticaIngresoSucursalDTO dto = new EstadisticaIngresoSucursalDTO();
                    dto.setSucursal((String) obj[0]);
                    dto.setIngresos(obj[1] != null ? ((Number) obj[1]).doubleValue() : 0.0);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 5. Ingresos en un periodo de tiempo
    public EstadisticaIngresoPeriodoDTO obtenerIngresosPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        Double ingresos = alquilerRepository.findIngresosPeridoTiempo(fechaInicio, fechaFin);

        EstadisticaIngresoPeriodoDTO dto = new EstadisticaIngresoPeriodoDTO();
        dto.setIngresos(ingresos != null ? ingresos : 0.0);
        dto.setFechaInicio(fechaInicio.toString());
        dto.setFechaFin(fechaFin.toString());

        return dto;
    }


    // 6. Ingresos generales
    public Double obtenerIngresosTotales() {
        return alquilerRepository.findIngresosTotales();
    }

    // 7. Cliente con más reservas
    public List<EstadisticaClienteDTO> obtenerTopClientes() {
        List<Object[]> resultados = alquilerRepository.findTopClientes();

        return resultados.stream()
                .map(obj -> {
                    Cliente cliente = (Cliente) obj[0];
                    Long cantidad = (Long) obj[1];

                    EstadisticaClienteDTO dto = new EstadisticaClienteDTO();
                    dto.setCliente(new PersonaDTO(cliente));
                    dto.setCantidadReservas(cantidad);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}

package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.estadisticas.*;
import inge2.com.alquileresMaria.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticasService service;

    // 1. Vehículos más alquilados por sucursal
    @GetMapping("/vehiculos-por-sucursal")
    public List<EstadisticaVehiculoSucursalDTO> obtenerVehiculosMasAlquiladosPorSucursal() {
        return service.obtenerVehiculosMasAlquiladosPorSucursalDTO();
    }

    // 2. Vehículos más alquilados (general)
    @GetMapping("/vehiculos-mas-alquilados")
    public List<EstadisticaVehiculoDTO> obtenerVehiculosMasAlquilados() {
        return service.obtenerVehiculosMasAlquiladosDTO();
    }

    // 3. Categoría más alquilada
    @GetMapping("/categorias-mas-alquiladas")
    public List<EstadisticaCategoriaDTO> obtenerCategoriasMasAlquiladas() {
        return service.obtenerCategoriasMasAlquiladasDTO();
    }

    // 4. Ingresos por sucursal
    @GetMapping("/ingresos-por-sucursal")
    public List<EstadisticaIngresoSucursalDTO> obtenerIngresosPorSucursal() {
        return service.obtenerIngresosPorSucursal();
    }

    // 5. Ingresos en un periodo de tiempo
    @GetMapping("/ingresos-periodo")
    public EstadisticaIngresoPeriodoDTO obtenerIngresosPeriodo(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return service.obtenerIngresosPeriodo(fechaInicio, fechaFin);
    }

    // 6. Ingresos totales
    @GetMapping("/ingresos-totales")
    public Double obtenerIngresosTotales() {
        return service.obtenerIngresosTotales();
    }

    // 7. Clientes con más reservas
    @GetMapping("/top-clientes")
    public List<EstadisticaClienteDTO> obtenerTopClientes() {
        return service.obtenerTopClientes();
    }
}


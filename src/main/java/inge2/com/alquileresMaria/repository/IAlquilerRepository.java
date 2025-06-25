package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IAlquilerRepository extends JpaRepository<Alquiler,Long> {

    @Query("""
    SELECT a
    FROM Alquiler a
    WHERE a.licenciaConductor = :licencia
        AND a.rangoFecha.fechaDesde <= :fechaHasta
        AND a.rangoFecha.fechaHasta >= :fechaDesde
        AND a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
    """)
    Optional<Alquiler> findAlquilerByLicenciaConductorAndRangoFecha (
            @Param("licencia") String licenciaConductor,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta
    );

    // Vehículos más alquilados por sucursal
    @Query("""
    SELECT a.sucursal.ciudad, a.auto, COUNT(a)
    FROM Alquiler a
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
        AND a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.sucursal.ciudad, a.auto
    ORDER BY COUNT(a) DESC
   """)
    List<Object[]> findMasAlquiladosSucursal();

    // Vehículos más alquilados (general)
    @Query("""
    SELECT a.auto, COUNT(a)
    FROM Alquiler a
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
        AND a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.auto
    ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findVehiculosMasAlquilados();

    // Categoría más alquilada
    @Query("""
    SELECT a.auto.categoria, COUNT(a)
    FROM Alquiler a
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
        AND a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.auto.categoria
    ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findCategoriasMasAlquiladas();

    // Ingresos por sucursal
    @Query("""
    SELECT a.sucursal.ciudad,
           SUM(CASE WHEN a.estadoAlquiler = inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
                    THEN (a.pago.monto - COALESCE(a.rembolso.montoRembolsado, 0))
                    ELSE a.pago.monto END)
    FROM Alquiler a
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.sucursal.ciudad
    """)
    List<Object[]> findIngresosSucursales();

    // Ingresos en un periodo de tiempo
    @Query("""
    SELECT SUM(CASE WHEN a.estadoAlquiler = inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
                    THEN (a.pago.monto - COALESCE(a.rembolso.montoRembolsado, 0))
                    ELSE a.pago.monto END)
    FROM Alquiler a
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
        AND a.rangoFecha.fechaDesde <= :fechaInicio
        AND a.rangoFecha.fechaHasta >= :fechaFin
    """)
    Double findIngresosPeridoTiempo(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    // Ingresos generales
    @Query("""
    SELECT SUM(CASE WHEN a.estadoAlquiler = inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
                    THEN (a.pago.monto - COALESCE(a.rembolso.montoRembolsado, 0))
                    ELSE a.pago.monto END)
    FROM Alquiler a
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    """)
    Double findIngresosTotales();

    // Cliente con más reservas
    @Query("""
    SELECT a.cliente, COUNT(a)
    FROM Alquiler a
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
        AND a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.cliente
    ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findTopClientes();

}
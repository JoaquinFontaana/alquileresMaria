package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.*;
import jakarta.validation.Valid;
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
    // Ingresos en un periodo de tiempo
    @Query("""
    SELECT SUM(
        CASE
            WHEN a.estadoAlquiler = inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
            THEN (p.monto - COALESCE(r.montoRembolsado, 0))
            ELSE p.monto
        END
    )
    FROM Alquiler a
    JOIN a.pago p
    LEFT JOIN a.rembolso r
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
      AND a.rangoFecha.fechaDesde <= :fechaFin
      AND a.rangoFecha.fechaHasta >= :fechaInicio
    """)
    Double findIngresosPeridoTiempo(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("""
    SELECT a.sucursal.ciudad, a.auto,
           COUNT(a),
           SUM(
               CASE 
                   WHEN a.estadoAlquiler = inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
                   THEN (p.monto - COALESCE(r.montoRembolsado, 0))
                   ELSE p.monto
               END
           )
    FROM Alquiler a
    JOIN a.pago p
    LEFT JOIN a.rembolso r
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
      AND a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.sucursal.ciudad, a.auto
    ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findMasAlquiladosSucursalConMonto();

    @Query("""
    SELECT a.auto, COUNT(a),
           SUM(
               CASE 
                   WHEN a.estadoAlquiler = inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
                   THEN (p.monto - COALESCE(r.montoRembolsado, 0))
                   ELSE p.monto
               END
           )
    FROM Alquiler a
    JOIN a.pago p
    LEFT JOIN a.rembolso r
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
      AND a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.auto
    ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findVehiculosMasAlquiladosConMonto();

    @Query("""
    SELECT a.auto.categoria, COUNT(a),
           SUM(
               CASE 
                   WHEN a.estadoAlquiler = inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
                   THEN (p.monto - COALESCE(r.montoRembolsado, 0))
                   ELSE p.monto
               END
           )
    FROM Alquiler a
    JOIN a.pago p
    LEFT JOIN a.rembolso r
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
      AND a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.auto.categoria
    ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findCategoriasMasAlquiladasConMonto();

    @Query("""
    SELECT a.cliente, COUNT(a),
           SUM(
               CASE 
                   WHEN a.estadoAlquiler = inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
                   THEN (p.monto - COALESCE(r.montoRembolsado, 0))
                   ELSE p.monto
               END
           )
    FROM Alquiler a
    JOIN a.pago p
    LEFT JOIN a.rembolso r
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
      AND a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.cliente
    ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findTopClientesConMonto();

    @Query("""
    SELECT 
        COALESCE(SUM(p.monto), 0),
        COUNT(DISTINCT a.id),
        COALESCE(SUM(r.montoRembolsado), 0),
        COUNT(DISTINCT r.id)
    FROM Alquiler a
    LEFT JOIN a.pago p ON p.estadoPago = 'PAGADO'
    LEFT JOIN a.rembolso r
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
    """)
    Object findResumenIngresosTotales();

    @Query("""
    SELECT a.sucursal.ciudad,
           SUM(
               CASE 
                   WHEN a.estadoAlquiler = inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CANCELADO
                   THEN (p.monto - COALESCE(r.montoRembolsado, 0))
                   ELSE p.monto
               END
           ),
           COUNT(a)
    FROM Alquiler a
    JOIN a.pago p
    LEFT JOIN a.rembolso r
    WHERE a.estadoAlquiler != inge2.com.alquileresMaria.model.enums.EstadoAlquiler.CONFIRMACION_PENDIENTE
    GROUP BY a.sucursal.ciudad
    """)
    List<Object[]> findIngresosYCantidadPorSucursal();





    List<Alquiler> findBySucursal_Ciudad(@Valid String ciudad);
}
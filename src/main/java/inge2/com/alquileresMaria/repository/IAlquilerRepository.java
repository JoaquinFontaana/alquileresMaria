package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface IAlquilerRepository extends JpaRepository<Alquiler,Long> {

    @Query("""
    SELECT a
    FROM Alquiler a
    WHERE a.licenciaConductor = :licencia
        AND a.rangoFecha.fechaDesde <= :fechaHasta
        AND a.rangoFecha.fechaHasta >= :fechaDesde
    """)
    Optional<Alquiler> findAlquilerByLicenciaConductorAndRangoFecha (
            @Param("licencia") String licenciaConductor,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta
    );

}

package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPagoRepository extends JpaRepository<Pago,Long> {
    Optional<Pago> findByPreferenceId(String preferenceId);
    Optional<Pago> findByAlquiler_id(Long alquilerId);
}

package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByNombre(String nombre);
    Optional<Rol> findByNombreIgnoreCase(String nombre);
}

package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmpleadoRepository extends JpaRepository<Empleado,Long>{
    boolean existsByMail(String mail);
    boolean existsByDni(String dni);
    Optional<Empleado> findByMail(String mail);
}

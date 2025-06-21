package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoRepository extends JpaRepository<Empleado,Long>{
    boolean existsByMail(String mail);
    boolean existsByDni(String dni);
}

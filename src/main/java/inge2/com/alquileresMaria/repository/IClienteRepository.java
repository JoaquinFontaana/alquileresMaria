package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente,Long> {
    boolean existsByMail(String email);
    boolean existsByDni(String dni);
    Optional<Cliente> findByMail(String mail);
}

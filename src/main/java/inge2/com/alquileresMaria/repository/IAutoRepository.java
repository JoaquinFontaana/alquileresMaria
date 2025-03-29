package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAutoRepository extends JpaRepository<Auto,Long> {
    public boolean existsByPatente(String patente);
}

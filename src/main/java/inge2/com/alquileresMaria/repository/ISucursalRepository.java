package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISucursalRepository extends JpaRepository<Sucursal,Long> {
    public boolean existsByCiudad(String ciudad);
}

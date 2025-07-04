package inge2.com.alquileresMaria.repository;

import inge2.com.alquileresMaria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {
    public Optional<Usuario> findByMail(String mail);
    public Optional<Usuario> findUsuarioByRolNombre(String rol);
    boolean existsByMail(String mail);
}

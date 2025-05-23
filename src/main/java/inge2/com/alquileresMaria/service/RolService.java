package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Rol;
import inge2.com.alquileresMaria.repository.IRolRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    @Autowired
    private IRolRepository rolRepository;

    public Rol findRolByNombre(String nombre) {
        return  this.rolRepository.findByNombre("Cliente").orElseThrow(() -> new EntityExistsException("El rol Cliente no existe"));
    }
}

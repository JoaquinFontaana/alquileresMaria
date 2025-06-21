package inge2.com.alquileresMaria.service.validators;

import inge2.com.alquileresMaria.repository.IEmpleadoRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoHelperService {
    private final IEmpleadoRepository empleadoRepository;
    @Autowired
    public EmpleadoHelperService(IEmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public void checkNotExistsDNI(String dni){
        if(empleadoRepository.existsByDni(dni)){
            throw new EntityExistsException("El dni " + dni + " ya existe");
        }
    }
    public void checkNotExistsMail(String mail){
        if(empleadoRepository.existsByMail(mail)){
            throw new EntityExistsException("El email " + mail + " ya existe");
        }
    }
}

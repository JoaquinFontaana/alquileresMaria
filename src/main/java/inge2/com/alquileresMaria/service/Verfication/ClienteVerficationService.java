package inge2.com.alquileresMaria.service.Verfication;

import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.repository.IClienteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteVerficationService {
    @Autowired
    private IClienteRepository clienteRepository;
    public void checkNotExistMail(String mail){
        if(clienteRepository.existsByMail(mail)){
            throw new EntityExistsException("El email " + mail + " ya existe");
        }
    }
    public void checkNotExistDni(String dni){
        if(clienteRepository.existsByDni(dni)){
            throw new EntityExistsException("El dni " + dni + " ya existe");
        }
    }
    public Cliente findClienteByEmail(String mail){
        return this.clienteRepository.findClienteByMail(mail)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con el mail " + mail + " no existe"));
    }
}

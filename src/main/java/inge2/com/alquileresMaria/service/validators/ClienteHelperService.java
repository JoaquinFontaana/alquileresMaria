package inge2.com.alquileresMaria.service.validators;

import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.repository.IClienteRepository;
import inge2.com.alquileresMaria.service.UsuarioService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteHelperService {

    private final IClienteRepository clienteRepository;
    private final UsuarioService usuarioService;

    public ClienteHelperService(IClienteRepository clienteRepository, UsuarioService usuarioService) {
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
    }

    public void checkNotExistDni(String dni){
        if(clienteRepository.existsByDni(dni)){
            throw new EntityExistsException("El dni " + dni + " ya existe");
        }
    }

    public void checkNotExistsCliente(PersonaDTO dto){
        this.checkNotExistDni(dto.getDni());
        this.usuarioService.checkNotExistsMail(dto.getMail());
    }

    public Cliente findClienteByEmail(String mail){
        return this.clienteRepository.findByMail(mail)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con el mail " + mail + " no existe"));
    }
    public Cliente findClienteById(Long id){
        return this.clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con el id " + id + " no existe"));
    }
}

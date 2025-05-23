package inge2.com.alquileresMaria.service;


import inge2.com.alquileresMaria.dto.PersonaDTO;
import inge2.com.alquileresMaria.dto.PersonaDtoPassword;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.Rol;
import inge2.com.alquileresMaria.repository.IClienteRepository;
import inge2.com.alquileresMaria.repository.IRolRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private EncryptService encryptService;
    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private RolService rolService ;

    @Transactional
    public void crearCliente(PersonaDtoPassword clienteDTO){
        this.checkNotExistMail(clienteDTO.getMail());
        this.checkNotExistDni(clienteDTO.getDni());
        clienteDTO.setPassword(encryptService.encryptPassword(clienteDTO.getPassword()));
        Cliente cliente = new Cliente(clienteDTO,rolService.findRolByNombre("Cliente"));
        clienteRepository.save(cliente);
    }
    private void checkNotExistMail(String mail){
        if(clienteRepository.existsByMail(mail)){
            throw new EntityExistsException("El email " + mail + " ya existe");
        }
    }
    private void checkNotExistDni(String dni){
        if(clienteRepository.existsByDni(dni)){
            throw new EntityExistsException("El dni " + dni + " ya existe");
        }
    }
}

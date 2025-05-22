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
    private IClienteRepository repository;
    @Autowired
    private IRolRepository rolRepository;

    @Transactional
    public void crearCliente(PersonaDtoPassword clienteDTO){
        if(repository.existsByMail(clienteDTO.getMail())){
            throw new EntityExistsException("El email " + clienteDTO.getMail() + " ya existe");
        }
        if(repository.existsByDni(clienteDTO.getDni())){
            throw new EntityExistsException("El dni " + clienteDTO.getDni() + " ya existe");
        }
        Rol rol = this.rolRepository.findByNombre("Cliente")
                .orElseThrow(() -> new EntityExistsException("El rol Cliente no existe"));
        clienteDTO.setPassword(encryptService.encryptPassword(clienteDTO.getPassword()));
        Cliente cliente = new Cliente(clienteDTO,rol);
        repository.save(cliente);
    }

}

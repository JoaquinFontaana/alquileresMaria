package inge2.com.alquileresMaria.service;


import inge2.com.alquileresMaria.dto.PersonaDTO;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.repository.IClienteRepository;
import inge2.com.alquileresMaria.service.Verfication.ClienteVerficationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private ClienteVerficationService clienteVerficationService;

    @Transactional
    public void crearCliente(PersonaDTO clienteDTO){
        this.clienteVerficationService.checkNotExistMail(clienteDTO.getMail());
        this.clienteVerficationService.checkNotExistDni(clienteDTO.getDni());

        clienteDTO.setPassword(encryptService.encryptPassword(clienteDTO.getPassword()));
        Cliente cliente = new Cliente(clienteDTO,rolService.findRolByNombre("CLIENT"));

        clienteRepository.save(cliente);
    }


}

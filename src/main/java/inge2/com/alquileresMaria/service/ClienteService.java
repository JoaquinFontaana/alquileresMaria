package inge2.com.alquileresMaria.service;


import inge2.com.alquileresMaria.dto.AlquilerDTOListar;
import inge2.com.alquileresMaria.dto.PersonaDTO;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.repository.IClienteRepository;
import inge2.com.alquileresMaria.service.helper.ClienteHelperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final EncryptService encryptService;
    private final IClienteRepository clienteRepository;
    private final RolService rolService ;
    private final ClienteHelperService clienteHelperService;

    @Autowired
    public ClienteService(EncryptService encryptService, IClienteRepository clienteRepository, RolService rolService, ClienteHelperService clienteHelperService) {
        this.encryptService = encryptService;
        this.clienteRepository = clienteRepository;
        this.rolService = rolService;
        this.clienteHelperService = clienteHelperService;
    }

    @Transactional
    public void crearCliente(PersonaDTO clienteDTO){
        this.clienteHelperService.checkNotExistMail(clienteDTO.getMail());
        this.clienteHelperService.checkNotExistDni(clienteDTO.getDni());

        clienteDTO.setPassword(encryptService.encryptPassword(clienteDTO.getPassword()));
        Cliente cliente = new Cliente(clienteDTO, rolService.findRolByNombre("CLIENT"));

        clienteRepository.save(cliente);
    }
    public List<AlquilerDTOListar> listarAlquileres(String email){
        return this.clienteHelperService.findClienteByEmail(email)
                .getAlquileres()
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }

}

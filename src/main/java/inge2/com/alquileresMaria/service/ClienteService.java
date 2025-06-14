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
    @Autowired
    private EncryptService encryptService;
    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private RolService rolService ;
    @Autowired
    private ClienteHelperService clienteHelperService;

    @Transactional
    public void crearCliente(PersonaDTO clienteDTO){
        this.clienteHelperService.checkNotExistMail(clienteDTO.getMail());
        this.clienteHelperService.checkNotExistDni(clienteDTO.getDni());

        clienteDTO.setPassword(encryptService.encryptPassword(clienteDTO.getPassword()));
        Cliente cliente = new Cliente(clienteDTO, rolService.findRolByNombre("CLIENT"));

        clienteRepository.save(cliente);
    }
    @Transactional
    public void registrarPagoMulta(String idCliente){
        Cliente cliente = this.clienteHelperService.findClienteById(Long.parseLong(idCliente));
        cliente.setMontoMulta(0);
        this.clienteRepository.save(cliente);
    }
    public List<AlquilerDTOListar> listarAlquileres(String email){
        return this.clienteHelperService.findClienteByEmail(email)
                .getAlquileres()
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }
    public double getMulta(String mail){
        return this.clienteHelperService.findClienteByEmail(mail).getMontoMulta();
    }
}

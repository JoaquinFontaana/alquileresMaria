package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Usuario;
import inge2.com.alquileresMaria.repository.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final IUsuarioRepository usuarioRepository;
    private final RolService rolService;
    private final EncryptService encryptService;
    @Autowired
    public UsuarioService(RolService rolService, IUsuarioRepository usuarioRepository, EncryptService encryptService) {
        this.rolService = rolService;
        this.usuarioRepository = usuarioRepository;
        this.encryptService = encryptService;
    }

    @Transactional
    public void crearAdmin(String mail,String password){
        String encryptedPassword = encryptService.encryptPassword(password);
        this.usuarioRepository.save(new Usuario(encryptedPassword,mail,this.rolService.findRolByNombre("ADMIN")));
    }

    public boolean checkNotExistsAdmin(){
        return this.usuarioRepository.findUsuarioByRolNombre("ADMIN").isEmpty();
    }
    public Usuario findByEmail(String mail) {
        return usuarioRepository.findByMail(mail)
                .orElseThrow(()-> new EntityNotFoundException("El usuario con mail "+ mail + " no se encontro"));
    }
}

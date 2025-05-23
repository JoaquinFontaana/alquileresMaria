package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Usuario;
import inge2.com.alquileresMaria.repository.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private EncryptService encryptService;

    public String iniciarSesion(String mail,String password){
        Usuario usuario = this.usuarioRepository.findByMail(mail)
                .orElseThrow(()-> new EntityNotFoundException("El mail no existe o la contraseña ingresada no coincide "));
        if(!this.encryptService.verifyPassword(password,usuario.getPassword())){
            //Contraseña incorrecta
        }
        String nombreRol = usuario.getRol().getNombre();
        //Buscar segun el rol, en la tabla empleado o cliente
        return "Hola";
    }
}

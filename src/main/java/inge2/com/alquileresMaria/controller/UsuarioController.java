package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.service.ClienteService;
import inge2.com.alquileresMaria.service.UsuarioService;
import inge2.com.alquileresMaria.service.helper.ClienteHelperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/recuperarPassword")
    public ResponseEntity<String> recuperarPassword(@RequestParam String mail){
        this.service.recuperarPassword(mail);
        return ResponseEntity.ok("Contraseña autogenerada enviada al mail");
    }
}

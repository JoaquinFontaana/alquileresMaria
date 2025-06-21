package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.AuthResponseDTO;
import inge2.com.alquileresMaria.dto.LoginDTO;
import inge2.com.alquileresMaria.security.JWTGenerator;
import inge2.com.alquileresMaria.service.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator tokenGenerator;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getMail(),
                        loginDto.getPassword()
                )
        );
        this.userService.sendDobleAutenticacion(loginDto.getMail());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =  tokenGenerator.generateToken(authentication);
        return ResponseEntity.ok().body(new AuthResponseDTO(token));
    }

    @GetMapping("/dobleAutenticacion")
    public boolean confimarCodigo(@RequestParam int cod){
        return this.userService.confirmarCodigo(cod);
    }
}

package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.AuthResponseDTO;
import inge2.com.alquileresMaria.dto.LoginDTO;
import inge2.com.alquileresMaria.dto.PersonaDTO;
import inge2.com.alquileresMaria.security.JWTGenerator;
import inge2.com.alquileresMaria.security.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

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
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =  tokenGenerator.generateToken(authentication);
        return ResponseEntity.ok().body(new AuthResponseDTO(token));
    }

}

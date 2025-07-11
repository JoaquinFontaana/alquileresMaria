package inge2.com.alquileresMaria.security;

import inge2.com.alquileresMaria.model.Empleado;
import inge2.com.alquileresMaria.repository.IEmpleadoRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JWTGenerator {
    @Autowired
    private IEmpleadoRepository empleadoRepository;

    public String generateToken(Authentication authentication){

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstant.JWT_EXPIRATION);

        // Decodificar la clave Base64 y crear un SecretKey seguro
        byte[] keyBytes = Base64.getDecoder().decode(SecurityConstant.JWT_SECRET);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        String roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Generar el token
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .claim("roles", roles);

        // Agregar sucursal si el rol es EMPLEADO
        if (roles.contains("EMPLEADO")) {
            // Buscar al empleado por ID
            Optional<Empleado> empleadoOpt = empleadoRepository.findById(userPrincipal.getId());

            if (empleadoOpt.isPresent()) {
                Empleado empleado = empleadoOpt.get();
                if (empleado.getTrabajaEnSucursal() != null) {
                    builder.claim("sucursal", empleado.getTrabajaEnSucursal().getCiudad());
                }
            }
        }

        return builder.signWith(key).compact();
    }

    public String getUsernameJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstant.JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey(this.getSigningKey())
                .build()
                .parseClaimsJws(token);
        return true;
    }
}

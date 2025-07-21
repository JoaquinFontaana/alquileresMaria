package inge2.com.alquileresMaria.security;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstant {
    public static final long JWT_EXPIRATION = 7000000;
    @Value("${JWT_SECRET}")
    public static String JWT_SECRET;

}

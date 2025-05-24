package inge2.com.alquileresMaria.configuration;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MPConfig {
    //Poner en variable de entorno (token de prueba)
    @Value("APP_USR-3434904925685691-052413-6cb378c98fb46c7223a3628a9d500b51-2454892997")
    private String accesToken;

    @PostConstruct
    public void init(){
        MercadoPagoConfig.setAccessToken(this.accesToken);
    }

}

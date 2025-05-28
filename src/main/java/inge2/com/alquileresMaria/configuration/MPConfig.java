package inge2.com.alquileresMaria.configuration;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MPConfig {
    @Value("${MERCADOPAGO_PROJECT_TOKEN}")
    private String accesToken;

    @PostConstruct
    public void init(){
        MercadoPagoConfig.setAccessToken(this.accesToken);
    }

}

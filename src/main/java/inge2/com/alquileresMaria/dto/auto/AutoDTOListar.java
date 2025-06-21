package inge2.com.alquileresMaria.dto.auto;

import inge2.com.alquileresMaria.model.Auto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutoDTOListar extends AutoDTO{
    private String endpointImagen;
    public AutoDTOListar(Auto auto) {
        super(auto);
        this.endpointImagen = "/auto/get/imagen?patente="+auto.getPatente();
    }

    public AutoDTOListar() {
        super();
    }
}

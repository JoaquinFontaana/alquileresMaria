package inge2.com.alquileresMaria.dto;

import inge2.com.alquileresMaria.model.Auto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutoDTOListar extends AutoDTO{
    private String imagenBase64;

    public AutoDTOListar(Auto auto, String imagenBase64) {
        super(auto);
        this.imagenBase64 = imagenBase64;
    }

    public AutoDTOListar() {
        super();
    }
}

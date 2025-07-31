package inge2.com.alquileresMaria.dto.auto;

import inge2.com.alquileresMaria.model.Auto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutoDTOListar extends AutoDTO{
    private String imgUrl;
    public AutoDTOListar(Auto auto) {
        super(auto);
        this.imgUrl = auto.getRutaImagen();
    }

    public AutoDTOListar() {
        super();
    }
}

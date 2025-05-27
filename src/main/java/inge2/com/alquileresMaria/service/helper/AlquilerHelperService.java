package inge2.com.alquileresMaria.service.helper;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Cliente;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlquilerHelperService {
    public List<Alquiler> filtrarAlquileresPosteriores(List<Alquiler> alquileres){
        return  alquileres.stream()
                .filter(alquiler -> alquiler.getRangoFecha().getFechaDesde().isAfter(LocalDate.now()))
                .toList();
    }
    public List<Cliente> obtenerClientesDeAlquileres(List<Alquiler> alquileres){
        return  alquileres.stream().map(Alquiler::getCliente).toList();
    }
    public List<Long> obtenerIdsDeAlquileres(List<Alquiler> alquileres){
        return  alquileres.stream().map(Alquiler::getId).toList();
    }
}

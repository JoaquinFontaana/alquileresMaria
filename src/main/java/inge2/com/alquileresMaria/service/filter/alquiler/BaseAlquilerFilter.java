package inge2.com.alquileresMaria.service.filter.alquiler;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAlquilerFilter implements AlquilerFilterComponent{
    private final IAlquilerRepository alquilerRepository;

    public BaseAlquilerFilter(IAlquilerRepository alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
    }

    @Override
    public List<Alquiler> getAlquileres() {
        return this.alquilerRepository.findAll();
    }
}

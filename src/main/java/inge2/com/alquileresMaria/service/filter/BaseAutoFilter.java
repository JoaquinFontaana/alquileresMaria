package inge2.com.alquileresMaria.service.filter;

import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAutoFilter implements IAutoFilter {
    @Autowired
    private IAutoRepository repository;

    @Override
    public List<Auto> listar() {
        return repository.findAll();
    }
}

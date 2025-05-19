package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {
    @Autowired
    private IAutoRepository repository;

    public Auto crearAuto(Auto auto){
        if(this.repository.existsByPatente(auto.getPatente())){
            //throw excepcion de que la patente ya existe
        }
        return repository.save(auto);
    }

    public List<Auto> listarAuto(){
        return this.repository.findAll();
    }
}

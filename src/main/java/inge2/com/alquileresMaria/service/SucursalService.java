package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.ISucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Service
@RequestMapping("/sucursal")
public class SucursalService {
    @Autowired
    private ISucursalRepository repository;
    public Sucursal crearSucursal(Sucursal sucursal){
        if(repository.existsByCiudad(sucursal.getCiudad())){
            //throw execption
        }
        return repository.save(sucursal);
    }
}

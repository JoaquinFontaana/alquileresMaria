package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.ISucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    @Transactional
    public Sucursal crearSucursal(Sucursal sucursal){
        if(repository.existsByCiudad(sucursal.getCiudad())){
            throw new EntityNotFoundException("La sucursal de " + sucursal.getCiudad() +" ya existe");
        }
        return repository.save(sucursal);
    }

}

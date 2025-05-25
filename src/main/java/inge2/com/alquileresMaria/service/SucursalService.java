package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.ISucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;


@Service
public class SucursalService {
    @Autowired
    private ISucursalRepository sucursalRepository;
    @Transactional
    public void crearSucursal(Sucursal sucursal){
        this.checkNotExistSucursal(sucursal);
        sucursalRepository.save(sucursal);
    }

    public Sucursal findSucursalByCiudad(String ciudad) {
        return sucursalRepository.findByCiudad(ciudad)
                .orElseThrow(() -> new EntityNotFoundException("No existe sucursal en la ciudad " + ciudad));
    }

    private void checkNotExistSucursal(Sucursal sucursal){
        if(sucursalRepository.existsByCiudad(sucursal.getCiudad())){
            throw new EntityNotFoundException("La sucursal de " + sucursal.getCiudad() +" ya existe");
        }
    }
}

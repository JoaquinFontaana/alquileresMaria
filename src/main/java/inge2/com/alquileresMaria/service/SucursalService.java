package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.ISucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Service
public class SucursalService {

    private final ISucursalRepository sucursalRepository;

    public SucursalService(ISucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    @Transactional
    public void crearSucursal(Sucursal sucursal){
        this.checkNotExistSucursal(sucursal);
        sucursalRepository.save(sucursal);
    }

    public Sucursal findSucursalByCiudad(String ciudad) {
        return sucursalRepository.findByCiudad(ciudad)
                .orElseThrow(() -> new EntityNotFoundException("No existe sucursal en la ciudad " + ciudad));
    }

    public List<String> listarSucursales(){
        return this.sucursalRepository.findAll()
                .stream()
                .map(Sucursal::getCiudad)
                .toList();
    }

    public List<EmpleadoDTO> listarEmpleadosSucursal(String ciudad){
        return this.findSucursalByCiudad(ciudad)
                .getEmpleados()
                .stream()
                .map(EmpleadoDTO::new)
                .toList();
    }

    private void checkNotExistSucursal(Sucursal sucursal){
        if(sucursalRepository.existsByCiudad(sucursal.getCiudad())){
            throw new EntityNotFoundException("La sucursal de " + sucursal.getCiudad() +" ya existe");
        }
    }
}

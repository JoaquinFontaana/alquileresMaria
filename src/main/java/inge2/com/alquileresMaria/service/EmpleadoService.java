package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.EmpleadoDTO;
import inge2.com.alquileresMaria.model.Empleado;
import inge2.com.alquileresMaria.model.Rol;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.IEmpleadoRepository;
import inge2.com.alquileresMaria.repository.IRolRepository;
import inge2.com.alquileresMaria.repository.ISucursalRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class EmpleadoService {

    @Autowired
    private EncryptService encryptService;
    @Autowired
    private IEmpleadoRepository repository;
    @Autowired
    private IRolRepository rolRepository;
    @Autowired
    private ISucursalRepository sucursalRepository;

 /*   public void crearEmpleado(EmpleadoDTO dto){
        if(repository.existsByMail(dto.getMail())){
            throw new EntityExistsException("El email " + dto.getMail() + " ya existe");
        }
        if(repository.existsByDNI(dto.getDni())){
            throw new EntityExistsException("El dni " + dto.getDni() + " ya existe");
        }
        Rol rol = this.rolRepository.findByNombre("Empleado")
                .orElseThrow(() -> new EntityExistsException("El rol Empleado no existe"));
        dto.setPassword(encryptService.encryptPassword(dto.getPassword()));

        Sucursal sucursal = this.sucursalRepository.findByCiudad(dto.getNombreSucursalTrabaja())
                .orElseThrow(()-> new EntityNotFoundException("la sucursal de la ciudad "+ dto.getNombreSucursalTrabaja()+ " no existe"));
        Empleado empleado = new Empleado(dto, sucursal, rol);
        this.repository.save(empleado);
    }
  */
}

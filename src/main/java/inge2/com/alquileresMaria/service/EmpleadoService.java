package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import inge2.com.alquileresMaria.dto.user.EmpleadoDTOActualizar;
import inge2.com.alquileresMaria.dto.user.PersonaDTO;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.Empleado;
import inge2.com.alquileresMaria.repository.IEmpleadoRepository;
import inge2.com.alquileresMaria.service.generator.PasswordGenerator;
import inge2.com.alquileresMaria.service.validators.ClienteHelperService;
import inge2.com.alquileresMaria.service.validators.EmpleadoHelperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final IEmpleadoRepository repository;
    private final EmpleadoHelperService empleadoHelper;
    private final SucursalService sucursalService;
    private final RolService rolService;
    private final EncryptService encryptService;
    private final EmailService emailService;

    @Autowired
    public EmpleadoService(IEmpleadoRepository repository, EmpleadoHelperService empleadoHelper, SucursalService sucursalService, RolService rolService, EncryptService encryptService, EmailService emailService) {
        this.repository = repository;
        this.empleadoHelper = empleadoHelper;
        this.sucursalService = sucursalService;
        this.rolService = rolService;
        this.encryptService = encryptService;
        this.emailService = emailService;
    }


    @Transactional
    public void crearEmpleado(EmpleadoDTO dto){
        this.empleadoHelper.checkNotExistsEmpleado(dto);

        Empleado empleado = new Empleado(
                dto,
                this.sucursalService.findSucursalByCiudad(dto.getTrabajaEnSucursal()),
                rolService.findRolByNombre("EMPLEADO"),
                encryptService.encryptPassword(this.emailService.sendContraseñaAutoGenerada(dto.getMail()))
        );

        this.repository.save(empleado);
    }

    @Transactional
    public void actualizarEmpleado(EmpleadoDTOActualizar empleadoDTO){
        Empleado empleado = this.empleadoHelper.findByMail(empleadoDTO.getMail());
        if(empleadoDTO.getNuevoMail() != null){
            this.empleadoHelper.checkNotExistMail(empleadoDTO.getNuevoMail());
        }
        if(empleadoDTO.getDni() != null){
            this.empleadoHelper.checkNotExistsDNI(empleado.getDni());
        }
        if(empleadoDTO.getTrabajaEnSucursal() != null){
            empleado.actualizarDatos(empleadoDTO,this.sucursalService.findSucursalByCiudad(empleadoDTO.getTrabajaEnSucursal()));
        }
        empleado.actualizarDatos(empleadoDTO);
    }

    public List<EmpleadoDTO> listarEmpleados(){
        return this.repository.findAll().stream().map(EmpleadoDTO::new).toList();
    }

}

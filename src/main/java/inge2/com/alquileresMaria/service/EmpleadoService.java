package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.user.EmpleadoDTO;
import inge2.com.alquileresMaria.model.Empleado;
import inge2.com.alquileresMaria.repository.IEmpleadoRepository;
import inge2.com.alquileresMaria.service.generator.PasswordGenerator;
import inge2.com.alquileresMaria.service.validators.EmpleadoHelperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    private final IEmpleadoRepository repository;
    private final EmpleadoHelperService empleadoHelper;
    private final SucursalService sucursalService;
    private final RolService rolService;
    private final EncryptService encryptService;
    private final PasswordGenerator passwordGenerator;
    private final EmailService emailService;

    @Autowired
    public EmpleadoService(IEmpleadoRepository repository, EmpleadoHelperService empleadoHelper, SucursalService sucursalService, RolService rolService, EncryptService encryptService, PasswordGenerator passwordGenerator, EmailService emailService) {
        this.repository = repository;
        this.empleadoHelper = empleadoHelper;
        this.sucursalService = sucursalService;
        this.rolService = rolService;
        this.encryptService = encryptService;
        this.passwordGenerator = passwordGenerator;
        this.emailService = emailService;
    }

    @Transactional
    public void crearEmpleado(EmpleadoDTO dto){
        this.empleadoHelper.checkNotExistsDNI(dto.getDni());
        this.empleadoHelper.checkNotExistsMail(dto.getMail());

        String password = this.passwordGenerator.generatePassword();
        emailService.sendEmail(dto.getMail(),"Contraseña de su nueva cuenta en Alquileres maria","Contraseña: "+ password);
        Empleado empleado = new Empleado(
                dto,
                this.sucursalService.findSucursalByCiudad(dto.getNombreSucursalTrabaja()),
                rolService.findRolByNombre("EMPLEADO"),
                encryptService.encryptPassword(password)
        );

        this.repository.save(empleado);
    }

}

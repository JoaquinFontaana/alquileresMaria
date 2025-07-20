package inge2.com.alquileresMaria.useCase.Alquiler;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOCrear;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Cliente;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.SucursalService;
import inge2.com.alquileresMaria.service.validators.AlquilerHelperService;
import inge2.com.alquileresMaria.service.validators.AutoHelperService;
import inge2.com.alquileresMaria.service.validators.ClienteHelperService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CrearAlquilerUseCase {

    private final AlquilerHelperService alquilerHelperService;
    private final AutoHelperService autoHelperService;
    private final SucursalService sucursalService;
    private final ClienteHelperService clienteHelperService;
    private final AlquilerService alquilerService;

    public CrearAlquilerUseCase(AlquilerHelperService alquilerHelperService, AutoHelperService autoHelperService, SucursalService sucursalService, ClienteHelperService clienteHelperService, AlquilerService alquilerService) {
        this.alquilerHelperService = alquilerHelperService;
        this.autoHelperService = autoHelperService;
        this.sucursalService = sucursalService;
        this.clienteHelperService = clienteHelperService;
        this.alquilerService = alquilerService;
    }

    @Transactional
    public Alquiler crearAlquiler(AlquilerDTOCrear alquilerDTO, String mail){
        this.alquilerHelperService.checkDuracionAlquiler(alquilerDTO.getRangoFecha());
        this.alquilerHelperService.checkDisponibilidadConductor(alquilerDTO.getRangoFecha(),alquilerDTO.getLicenciaConductor());

        Auto auto = this.autoHelperService.findAutoByPatente(alquilerDTO.getPatenteAuto());
        this.autoHelperService.verificarDisponibilidad(auto,alquilerDTO.getRangoFecha());

        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(alquilerDTO.getSucursal());

        Cliente cliente = this.clienteHelperService.findClienteByEmail(mail);

        Alquiler alquiler = new Alquiler(alquilerDTO,auto,cliente,sucursal);

        return this.alquilerService.saveAlquiler(alquiler);
    }

}

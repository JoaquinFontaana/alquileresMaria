package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.dto.auto.AutoDTOActualizar;
import inge2.com.alquileresMaria.dto.auto.AutoDTOCrear;
import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.dto.auto.AutoFilterDTO;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.Sucursal;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import inge2.com.alquileresMaria.service.builder.AutoFilterBuilder;
import inge2.com.alquileresMaria.service.filter.auto.IAutoFilter;
import inge2.com.alquileresMaria.service.validators.AutoHelperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {
    private final IAutoRepository autoRepository;
    private final SucursalService sucursalService;
    private final AutoHelperService autoHelperService;
    private final AutoFilterBuilder autoFilterBuilder;
    private final FileStorageService fileStorageService;

    @Autowired
    public AutoService(IAutoRepository autoRepository, SucursalService sucursalService, AutoHelperService autoHelperService, AutoFilterBuilder autoFilterBuilder, FileStorageService fileStorageService) {
        this.autoRepository = autoRepository;
        this.sucursalService = sucursalService;
        this.autoHelperService = autoHelperService;
        this.autoFilterBuilder = autoFilterBuilder;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public void crearAuto(AutoDTOCrear autoDto){
        this.fileStorageService.checkImagen(autoDto.getImagen());
        this.autoHelperService.checkPatenteNotExists(autoDto.getPatente());

        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(autoDto.getSucursal());

        String rutaImagen = fileStorageService.guardarImagen(autoDto.getImagen());

        Auto auto = new Auto(autoDto,sucursal,rutaImagen);
        autoRepository.save(auto);
    }

    public List<AutoDTOListar> listarAutos(AutoFilterDTO opcionesFiltrado){
        IAutoFilter filter = this.autoFilterBuilder.buildFilter(opcionesFiltrado);
        return filter
                .listar()
                .stream()
                .map(auto -> new AutoDTOListar(auto))
                .toList();
    }
    @Transactional
    public void saveAuto(Auto auto){
        this.autoRepository.save(auto);
    }

    @Transactional
    public void actualizarAuto(AutoDTOActualizar autoActualizado){
        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(autoActualizado.getSucursal());
        Auto auto = this.autoHelperService.findAutoByPatente(autoActualizado.getPatente());
        if(autoActualizado.getImagen() != null){
            fileStorageService.borrarArchivoSiExiste(auto.getRutaImagen());
            auto.actualizarAutoImagen(autoActualizado,sucursal,fileStorageService.guardarImagen(autoActualizado.getImagen()));
        }
        else{
            auto.actualizarAuto(autoActualizado,sucursal);
        }
        this.autoRepository.save(auto);
    }

    public byte[] obtenerImagenAuto(String patente){
        String rutaImagen =this.autoHelperService.findAutoByPatente(patente).getRutaImagen();
        return this.fileStorageService.leerImagenComoBytes(rutaImagen);
    }

    @Transactional
    public void finalizarMantenimiento(String patente) {
        this.autoHelperService.findAutoByPatente(patente).finalizarMantenimiento(this);
    }
}

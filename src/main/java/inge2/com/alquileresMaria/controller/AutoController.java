package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.auto.AutoDTOActualizar;
import inge2.com.alquileresMaria.dto.auto.AutoDTOCrear;
import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.dto.auto.AutoFilterDTO;
import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import inge2.com.alquileresMaria.model.enums.EstadoAuto;
import inge2.com.alquileresMaria.model.enums.TiposRembolso;
import inge2.com.alquileresMaria.service.AutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auto")
public class AutoController {

    private final AutoService autoService;

    @Autowired
    public AutoController(AutoService autoService) {
        this.autoService = autoService;
    }

    @PutMapping("/eliminar")
    public ResponseEntity<String> eliminarAuto(@RequestParam @NotBlank String patente){
        this.autoService.eliminarAuto(patente);
        return ResponseEntity.ok("Auto eliminado con exito");
    }

    @PostMapping(path = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> crearAuto(@Valid @ModelAttribute AutoDTOCrear autoDto){
        this.autoService.crearAuto(autoDto);
        return ResponseEntity.ok("Auto creado con exito");
    }

    @PutMapping(path= "/actualizar", consumes =MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> actualizarAuto(@Valid @ModelAttribute AutoDTOActualizar autoActualizado){
        this.autoService.actualizarAuto(autoActualizado);
        return ResponseEntity.ok("Auto actualizado con exito");
    }
    @GetMapping("/get/imagen")
    public ResponseEntity<byte[]> getImagen(@NotBlank @RequestParam String patente){
        byte[] data = this.autoService.obtenerImagenAuto(patente);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }
    @GetMapping("/get/categorias")
    public List<CategoriaAuto> getCategorias(){
        return List.of(CategoriaAuto.values());
    }

    @GetMapping("/get/estados")
    public List<EstadoAuto> getEstados(){
        return List.of(EstadoAuto.values());
    }

    @GetMapping("/get/rembolsos")
    public List<TiposRembolso> getRembolsos(){
        return List.of(TiposRembolso.values());
    }
    /**
     * Lista los autos disponibles aplicando filtros combinados (todos opcionales).
     * <p>Los filtros se envían como parámetros de query, y se combinan con lógica AND.</p>
     *
     * Filtros disponibles:
     * <ul>
     *     <li><b>nombreSucursal</b>: nombre de la sucursal (String).</li>
     *     <li><b>fechaDesde</b> y <b>fechaHasta</b>: rango de fechas deseado (formato yyyy-MM-dd).</li>
     *     <li><b>capacidad</b>: capacidad mínima requerida (entero positivo).</li>
     *     <li><b>categorias</b>: lista de categorías (puede repetirse en la query).</li>
     * </ul>
     *
     * Ejemplo de request:
     * <pre>
     * GET /auto/listar?nombreSucursal=La%20Plata&fechaDesde=2025-06-01&fechaHasta=2025-06-10&capacidad=5&categorias=SEDAN&categorias=CAMIONETA
     * </pre>
     *
     * @param opcionesFiltrado objeto con los parámetros de filtrado, mapeado desde query parameters.
     * @return lista de autos disponibles que cumplen los filtros.
    @Operation(
            summary = "Listar autos con filtros opcionales",
            description = "Permite filtrar autos por nombre de sucursal, rango de fechas, capacidad mínima y categorías. Todos los filtros son opcionales y se combinan con lógica AND."
    )*/
    @GetMapping("/listar")
    public List<AutoDTOListar> listarAutos(@ModelAttribute AutoFilterDTO opcionesFiltrado){
        return this.autoService.listarAutos(opcionesFiltrado);
    }
}

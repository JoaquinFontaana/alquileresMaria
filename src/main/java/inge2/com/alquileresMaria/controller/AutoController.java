package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.AutoDTO;
import inge2.com.alquileresMaria.dto.AutoFilterDTO;
import inge2.com.alquileresMaria.service.AutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auto")
public class AutoController {

    @Autowired
    private AutoService service;

    @PostMapping("/crear")
    public ResponseEntity<String> crearAuto(@Valid @RequestBody AutoDTO autoDto){
        this.service.crearAuto(autoDto);
        return ResponseEntity.ok("Auto creado con exito");
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
    public List<AutoDTO> listarAutos(@ModelAttribute AutoFilterDTO opcionesFiltrado){
        return this.service.listarAutos(opcionesFiltrado);
    }
}

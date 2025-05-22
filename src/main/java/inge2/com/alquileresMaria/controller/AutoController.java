package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.dto.AutoDtoListar;
import inge2.com.alquileresMaria.dto.AutoFilterDTO;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.service.AutoService;
import inge2.com.alquileresMaria.service.Filter.BaseAutoFilter;
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
    public ResponseEntity<String> crearAuto(@Valid @RequestBody Auto auto){
        this.service.crearAuto(auto);
        return ResponseEntity.ok("Auto creado con exito");
    }


    @GetMapping("/listar")
    public List<AutoDtoListar> listarAutos(@Valid @ModelAttribute AutoFilterDTO opcionesFiltrado){
        return this.service.listarAutos(opcionesFiltrado);
    }
}

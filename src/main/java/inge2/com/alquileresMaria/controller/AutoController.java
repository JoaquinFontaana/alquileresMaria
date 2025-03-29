package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.repository.IAutoRepository;
import inge2.com.alquileresMaria.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auto")
public class AutoController {

    @Autowired
    private AutoService service;
    @PostMapping("/crear")
    public void crearAuto(@RequestBody Auto auto){
        
    }

}

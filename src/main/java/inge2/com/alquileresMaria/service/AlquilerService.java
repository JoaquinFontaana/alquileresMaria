package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.repository.IAlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlquilerService {

    @Autowired
    private IAlquilerRepository repository;

    public void cancelarReservas(List<Long> ids){
        //ToDo enviar mails de cancelación
        this.repository.deleteAllById(ids);
    }
}

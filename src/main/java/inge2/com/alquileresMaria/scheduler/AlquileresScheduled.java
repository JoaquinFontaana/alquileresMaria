package inge2.com.alquileresMaria.scheduler;

import inge2.com.alquileresMaria.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AlquileresScheduled {

    private final PagoService pagoService;
    @Autowired
    public AlquileresScheduled(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Scheduled(fixedDelay = 600000)
    public void deletePagosPendientes(){
        this.pagoService.deletePagosPendientes();
    }
}

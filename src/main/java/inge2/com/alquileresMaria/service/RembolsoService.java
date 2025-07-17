package inge2.com.alquileresMaria.service;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Rembolso;
import inge2.com.alquileresMaria.model.enums.EstadoPago;
import inge2.com.alquileresMaria.model.enums.TiposRembolso;
import inge2.com.alquileresMaria.repository.IRembolsoRepository;
import inge2.com.alquileresMaria.service.builder.MpPreferenceBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RembolsoService {

    private final MpPreferenceBuilder mpPreferenceBuilder;
    private final IRembolsoRepository rembolsoRepository;

    public RembolsoService(MpPreferenceBuilder mpPreferenceBuilder, IRembolsoRepository rembolsoRepository) {
        this.mpPreferenceBuilder = mpPreferenceBuilder;
        this.rembolsoRepository = rembolsoRepository;
    }
    @Transactional
    public void crearRembolso(Alquiler reserva){
        if(reserva.getAuto().getRembolso() != TiposRembolso.SIN_REMBOLSO) {
            mpPreferenceBuilder.rembolsar(reserva.calcularRembolso(), reserva.getPago().getPaymentId());
        }
        reserva.getPago().setEstadoPago(EstadoPago.REMBOLSADO);
        rembolsoRepository.save(new Rembolso(reserva));
    }
    @Transactional
    public void crearRembolsoTotal(Alquiler reserva){
        mpPreferenceBuilder.rembolsar(reserva.calcularTotal(),reserva.getPago().getPaymentId());
        reserva.getPago().setEstadoPago(EstadoPago.REMBOLSADO);
        rembolsoRepository.save(new Rembolso(reserva,reserva.calcularTotal()));
    }
}

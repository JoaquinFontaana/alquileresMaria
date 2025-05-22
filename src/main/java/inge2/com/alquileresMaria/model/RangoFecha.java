package inge2.com.alquileresMaria.model;


import jakarta.persistence.Embeddable;

import java.time.LocalDate;
@Embeddable
public class RangoFecha {
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public RangoFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }
    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    /**
     * Devuelve true si este rango y el rango dado NO coinciden ni un solo día.
     * Es decir, están completamente separados:
     *   - este.fechaHasta < rango.fechaDesde  (termina antes de que empiece el otro)
     *   OR
     *   - rango.fechaHasta < this.fechaDesde  (el otro termina antes de que empiece éste)
     *
     * Igualdades en los límites también cuentan como solapamiento,
     * por lo que usamos isBefore/isAfter estrictos.
     */
    public boolean sinSolapamiento(RangoFecha rango) {
        return this.fechaHasta.isBefore(rango.getFechaDesde())
                || rango.getFechaHasta().isBefore(this.fechaDesde);
    }
}

package inge2.com.alquileresMaria.model.enums;

public enum Rembolso {
    SIN_REMBOLSO(0.0),
    REMBOLSO_PARCIAL(0.25),
    REMBOLSO_MEDIO(0.50),
    REMBOLSO_TOTAL(1.0);

    private final double porcentaje;

    Rembolso(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double calcularRembolso(double montoTotal) {
        return montoTotal * porcentaje;
    }
}

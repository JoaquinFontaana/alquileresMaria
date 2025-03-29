package inge2.com.alquileresMaria.model;

public enum Rembolso {
    SIN_REMBOLSO(0),
    REMBOLSO_PARCIAL(25),
    REMBOLSO_MEDIO(50),
    REMBOLSO_TOTAL(100);

    private final double porcentaje;

    Rembolso(double porcentaje){
        this.porcentaje = porcentaje;
    }

    public double calcularRembolso(double total){
        return 0;
    }


}

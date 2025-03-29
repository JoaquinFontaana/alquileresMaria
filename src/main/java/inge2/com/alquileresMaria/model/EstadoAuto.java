package inge2.com.alquileresMaria.model;

public enum EstadoAuto {
    EN_MANTENIMIENTO(false),
    ALQUILADO(false),
    DISPONIBLE(true);

    EstadoAuto(boolean esAlquilable){
        this.esAlquilable = esAlquilable;
    }

    private final boolean esAlquilable;

    public boolean esAlquilable(){
        return this.esAlquilable;
    }
}

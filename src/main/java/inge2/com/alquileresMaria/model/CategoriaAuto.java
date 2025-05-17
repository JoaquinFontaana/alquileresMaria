package inge2.com.alquileresMaria.model;

public enum CategoriaAuto {
    COMPACTO("Compacto"),
    SEDAN("Sedán"),
    SUV("SUV"),
    PICKUP("Pickup");

    private final String nombre;
    CategoriaAuto(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

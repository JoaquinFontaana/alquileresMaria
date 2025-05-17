package inge2.com.alquileresMaria.model;

public enum CategoriaAuto {
    ;
    //todo Poner categorias

    private final String nombre;
    CategoriaAuto(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

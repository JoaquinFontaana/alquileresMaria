package inge2.com.alquileresMaria.model;

public enum Rol {
    ADMIN("Administrador"),
    EMPLEADO("Empleado"),
    Cliente("Cliente");

    private final String nombre;
    Rol(String nombre){
        this.nombre = nombre;
    }
}

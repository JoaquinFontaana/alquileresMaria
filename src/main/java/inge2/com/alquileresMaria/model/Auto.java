package inge2.com.alquileresMaria.model;

import inge2.com.alquileresMaria.dto.auto.AutoDTO;
import inge2.com.alquileresMaria.dto.auto.AutoDTOActualizar;
import inge2.com.alquileresMaria.model.enums.CategoriaAuto;
import inge2.com.alquileresMaria.model.enums.EstadoAlquiler;
import inge2.com.alquileresMaria.model.enums.EstadoAuto;
import inge2.com.alquileresMaria.model.enums.TiposRembolso;
import inge2.com.alquileresMaria.model.valueObject.RangoFecha;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Entity
@Getter @Setter
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La patente es obligatoria")
    private String patente;
    @Positive(message = "La capacidad debe ser mayor a cero")
    private int capacidad;
    @NotBlank(message = "La marca es obligatoria")
    private String marca;
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;
    @NotNull(message = "El precio por dia es obligatorio")
    @Positive(message = "El precio por dia debe ser positivo")
    private double precioPorDia;
    @Enumerated(EnumType.STRING)
    private CategoriaAuto categoria;
    @Enumerated(EnumType.STRING)
    private TiposRembolso rembolso;
    @Enumerated(EnumType.STRING)
    private EstadoAuto estado;
    @OneToMany(mappedBy = "auto")
    private List<Alquiler> reservas;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
    @NotBlank(message = "La imagen es obligatoria")
    private String rutaImagen;
    private LocalDate fechaBaja = null;

    public Auto(){
        // Constructor por defecto requerido por Hibernate
    }


    public Auto(AutoDTO dto, Sucursal sucursal, String rutaImagen) {
        this.asignarDTOaAuto(dto);
        this.sucursal = sucursal;
        this.rutaImagen = rutaImagen;
    }

    public void actualizarAuto(AutoDTOActualizar dto, Sucursal sucursal){
        if (dto.getPrecioPorDia() != null) {
            this.precioPorDia = dto.getPrecioPorDia();
        }
        if (dto.getCategoria() != null) {
            this.categoria = dto.getCategoria();
        }
        if (dto.getRembolso() != null) {
            this.rembolso = dto.getRembolso();
        }
        if (dto.getEstado() != null) {
            this.estado = dto.getEstado();
        }
        this.sucursal = sucursal;
    }


    public void actualizarAutoImagen(AutoDTOActualizar dto, Sucursal sucursal,String rutaImagen){
        this.actualizarAuto(dto,sucursal);
        this.rutaImagen = rutaImagen;
    }

    private void asignarDTOaAuto(AutoDTO dto){
        this.patente = dto.getPatente();
        this.capacidad = dto.getCapacidad();
        this.marca = dto.getMarca();
        this.modelo = dto.getModelo();
        this.precioPorDia = dto.getPrecioPorDia();
        this.categoria = dto.getCategoria();
        this.rembolso = dto.getRembolso();
        this.estado = dto.getEstado();
    }
    public boolean disponibleEnRangoFechas(RangoFecha rango){
        return this.getReservas().stream().filter(alquiler -> alquiler.getEstadoAlquiler() != EstadoAlquiler.CANCELADO).allMatch(alquiler -> alquiler.sinSolapamiento(rango));
    }
    public boolean estaDisponible(){
        return this.estado == EstadoAuto.DISPONIBLE;
    }
    public void iniciarAlquiler(){
        this.estado = EstadoAuto.ALQUILADO;
    }
    public void finalizarAlquiler(){
        this.estado = EstadoAuto.DISPONIBLE;
    }
    public void ponerEnMantenimiento(){
        this.estado = EstadoAuto.EN_MANTENIMIENTO;
    }

    public boolean enUso() {
        return this.estado == EstadoAuto.ALQUILADO;
    }

    public void borradoLogico() {
        this.setEstado(EstadoAuto.BAJA);
        this.fechaBaja = LocalDate.now();
    }
}

package com.example.sport4party.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Evento implements Serializable {

    private int ID;
    private String descripcion;
    private Date fecha;
    private String nivelHabilidad;
    private String nombre;
    private String precio;
    private boolean pago;
    //relaciones
    private Deporte deporte;
    private Ubicacion ubicacion;

    //Constructor
    public Evento(int ID, String descripcion, Date fecha, String nivelHabilidad, String nombre, String precio, boolean pago, Deporte deporte, Ubicacion ubicacion) {
        this.ID = ID;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.nivelHabilidad = nivelHabilidad;
        this.nombre = nombre;
        this.precio = precio;
        this.pago = pago;
        this.deporte = deporte;
        this.ubicacion = ubicacion;
    }

    //Getters and setters
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getNivelHabilidad() {
        return nivelHabilidad;
    }
    public void setNivelHabilidad(String nivelHabilidad) {
        this.nivelHabilidad = nivelHabilidad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public boolean isPago() {
        return pago;
    }
    public void setPago(boolean pago) {
        this.pago = pago;
    }
    public Deporte getDeporte() {
        return deporte;
    }
    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}

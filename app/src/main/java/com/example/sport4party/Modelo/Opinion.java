package com.example.sport4party.Modelo;
import java.io.Serializable;

public class Opinion implements Serializable {

    private Float calificacion;
    private String descripcion;

    //Relaciones
    //private Ubicacion detalles;
    private Deportista remitente;

    public Opinion(Float calificacion, String descripcion) {
        this.calificacion = calificacion;
        this.descripcion = descripcion;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Deportista getRemitente() {
        return remitente;
    }

    public void setRemitente(Deportista remitente) {
        this.remitente = remitente;
    }
}
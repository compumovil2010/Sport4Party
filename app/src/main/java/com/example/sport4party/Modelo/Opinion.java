package com.example.sport4party.Modelo;

import java.io.Serializable;

public class Opinion implements Serializable {

    private Float calificacion;
    private String descripcion;
    //Relaciones
    private Ubicacion detalles;
    private Jugador remitente;

    //Constructor
    public Opinion(Float calificacion, String descripcion, Ubicacion detalles, Jugador remitente) {
        this.calificacion = calificacion;
        this.descripcion = descripcion;
        this.detalles = detalles;
        this.remitente = remitente;
    }

    //Getters and setters
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
    public Ubicacion getDetalles() {
        return detalles;
    }
    public void setDetalles(Ubicacion detalles) {
        this.detalles = detalles;
    }
    public Jugador getRemitente() {
        return remitente;
    }
    public void setRemitente(Jugador remitente) {
        this.remitente = remitente;
    }

}

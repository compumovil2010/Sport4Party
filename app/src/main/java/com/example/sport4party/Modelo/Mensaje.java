package com.example.sport4party.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Mensaje implements Serializable {

    private String contenido;
    private Date fecha;
    //relaciones
    private Jugador remitente;

    //Constructor
    public Mensaje(String contenido, Date fecha, Jugador remitente) {
        this.contenido = contenido;
        this.fecha = fecha;
        this.remitente = remitente;
    }

    //Getters and setters
    public String getContenido() {
        return contenido;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Jugador getRemitente() {
        return remitente;
    }
    public void setRemitente(Jugador remitente) {
        this.remitente = remitente;
    }
}
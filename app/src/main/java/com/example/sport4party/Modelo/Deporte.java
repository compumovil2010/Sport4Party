package com.example.sport4party.Modelo;

import java.io.Serializable;

public class Deporte implements Serializable {

    private Integer cantidadJugadores;
    private String nombre;

    //Constructor
    public Deporte(Integer cantidadJugadores, String nombre) {
        this.cantidadJugadores = cantidadJugadores;
        this.nombre = nombre;
    }

    //Getters and setters
    public Integer getCantidadJugadores() {
        return cantidadJugadores;
    }
    public void setCantidadJugadores(Integer cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

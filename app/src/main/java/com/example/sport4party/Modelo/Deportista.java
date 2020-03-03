package com.example.sport4party.Modelo;

import java.io.Serializable;

public class Deportista implements Serializable {
    private int ID;
    private String nombre;
    private String nivelDeHabilidad;
    private float popularidad;

    public Deportista(int ID, String nombre, String nivelDeHabilidad, float popularidad) {
        this.ID = ID;
        this.nombre = nombre;
        this.nivelDeHabilidad = nivelDeHabilidad;
        this.popularidad = popularidad;
    }

    public int getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNivelDeHabilidad() {
        return nivelDeHabilidad;
    }

    public float getPopularidad() {
        return popularidad;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNivelDeHabilidad(String nivelDeHabilidad) {
        this.nivelDeHabilidad = nivelDeHabilidad;
    }

    public void setPopularidad(float popularidad) {
        this.popularidad = popularidad;
    }
}

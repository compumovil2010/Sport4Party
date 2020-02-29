package com.example.sport4party.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Deportista implements Serializable {
    private int id;
    private String nombre;
    private String nivelHabilidad;
    private float popularidad;
    private ArrayList<Deportista> amigos;
    //private ArrayList<Evento> eventos;
    //private ArrayList<Evento> misEventos;

    public Deportista(int id, String nombre, String nivelHabilidad, float popularidad) {
        this.id = id;
        this.nombre = nombre;
        this.nivelHabilidad = nivelHabilidad;
        this.popularidad = popularidad;
        this.amigos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivelHabilidad() {
        return nivelHabilidad;
    }

    public void setNivelHabilidad(String nivelHabilidad) {
        this.nivelHabilidad = nivelHabilidad;
    }

    public float getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(float popularidad) {
        this.popularidad = popularidad;
    }

    public ArrayList<Deportista> getAmigos() {
        return amigos;
    }

    public void setAmigos(ArrayList<Deportista> amigos) {
        this.amigos = amigos;
    }

    public void addAmigo(Deportista amigo){
        this.amigos.add(amigo);
    }

    public int sizeAmigos(){
        return this.amigos.size();
    }
}

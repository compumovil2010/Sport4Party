package com.example.sport4party.Modelo;

import java.io.Serializable;


import java.util.ArrayList;

public class Deportista implements Serializable {
    private int id;
    private String nombre;
    private String nivelHabilidad;
    private float popularidad;
    private ArrayList<Deportista> amigos;
    private ArrayList<Evento> eventos;
    //private ArrayList<Evento> misEventos;

    public Deportista(int id, String nombre, String nivelHabilidad, float popularidad) {
        this.id = id;
        this.nombre = nombre;
        this.nivelHabilidad = nivelHabilidad;
        this.popularidad = popularidad;
        this.amigos = new ArrayList<>();
        this.eventos = new ArrayList<>();
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

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }

    public void addAmigo(Deportista amigo){
        this.amigos.add(amigo);
    }

    public int sizeAmigos(){ return this.amigos.size(); }

    public void addEvento(Evento evento){
        this.eventos.add(evento);
    }

    public int sizeEventos(){
        return this.eventos.size();
    }
}

package com.example.sport4party.Modelo;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jugador extends Usuario {

    private List<Jugador> amigos;
    private List<Mensaje> enviados;
    private List<Opinion> opiniones;
    private List<Evento> eventos;
    private Ubicacion ubicacionActual;


    //Constructores
    public Jugador(String contraseña, String correo, String nombreUsuario, String sexo) {
        super(contraseña, correo, nombreUsuario, sexo);
        amigos = new ArrayList<>();
        enviados = new ArrayList<>();
        opiniones = new ArrayList<>();
        eventos = new ArrayList<>();
    }
    public Jugador(String contraseña, String correo, Bitmap imagenPerfil, String nombreUsuario, String sexo) {
        super(contraseña, correo, imagenPerfil, nombreUsuario, sexo);
        amigos = new ArrayList<>();
        enviados = new ArrayList<>();
        opiniones = new ArrayList<>();
        eventos = new ArrayList<>();
    }

    //adders
    public void addAmigos(Jugador jugador) {
        amigos.add(jugador);
    }
    public void addEnviados(Mensaje mensaje) {
        enviados.add(mensaje);
    }
    public void addOpiniones(Opinion opinion) {
        opiniones.add(opinion);
    }
    public void addEventos(Evento evento) {
        eventos.add(evento);
    }

    //getters and setters
    public List<Jugador> getAmigos() {
        return amigos;
    }
    public void setAmigos(List<Jugador> amigos) {
        this.amigos = amigos;
    }
    public List<Mensaje> getEnviados() {
        return enviados;
    }
    public void setEnviados(List<Mensaje> enviados) {
        this.enviados = enviados;
    }
    public List<Opinion> getOpiniones() {
        return opiniones;
    }
    public void setOpiniones(List<Opinion> opiniones) {
        this.opiniones = opiniones;
    }
    public List<Evento> getEventos() {
        return eventos;
    }
    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
    public Ubicacion getUbicacionActual() {
        return ubicacionActual;
    }
    public void setUbicacionActual(Ubicacion ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }
}

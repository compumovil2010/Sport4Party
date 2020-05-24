package com.example.sport4party.Modelo;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.sport4party.Modelo.*;
import com.example.sport4party.Utils.Almacenamiento;

public class Jugador extends Usuario {

    private String id;
    private List<Jugador> amigos;
    private List<Mensaje> enviados;
    private List<Opinion> opiniones;
    private List<Evento> eventos;
    private List<Evento> eventosCreados;
    private Ubicacion ubicacionActual;


    //Constructores
    public Jugador(){
        super();
    }

    public Jugador(String correo, String nombreUsuario, String sexo, String tipo) {
        super(correo, nombreUsuario, sexo, tipo);
        amigos = new ArrayList<>();
        enviados = new ArrayList<>();
        opiniones = new ArrayList<>();
        eventos = new ArrayList<>();
        eventosCreados = new ArrayList<>();
    }

    public Jugador( String contraseña, String correo, String imagenPerfil, String nombreUsuario, String sexo) {
        super(contraseña, correo, imagenPerfil, nombreUsuario, sexo);
        amigos = new ArrayList<>();
        enviados = new ArrayList<>();
        opiniones = new ArrayList<>();
        eventos = new ArrayList<>();
        eventosCreados = new ArrayList<>();

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
    public void addEventoCreado(Evento evento) {eventosCreados.add(evento);}

    //getters and setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Evento> getEventosCreados() {
        return eventosCreados;
    }
    public void setEventosCreados(List<Evento> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }
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
    public void pushFireBaseBD() {
        final HashMap<String, Object> retorno = new HashMap<String, Object>();

        final HashMap<String, Object> amigos = new HashMap<String, Object>();
        if (this.amigos != null) {
            for (Jugador jugador : this.amigos) {
                amigos.put(jugador.getId(), jugador.getId());
            }
            retorno.put("amigos", amigos);
        }

        final HashMap<String, Object> enviados = new HashMap<String, Object>();
        if (this.enviados != null) ;
        for (Mensaje mensaje : this.enviados) {
            enviados.put(mensaje.getId(), mensaje.getId());
        }
        retorno.put("enviados", enviados);

        final HashMap<String, Object> opiniones = new HashMap<String, Object>();
        if (this.opiniones != null) {
            for (Opinion opinion : this.getOpiniones()) {
                opiniones.put(opinion.getId(),opinion.getId());
            }
            retorno.put("opiniones", opiniones);
        }


        final HashMap<String, Object>eventos= new HashMap<String,Object>();
        if(this.eventos!=null) {
            for (Evento evento : this.eventos) {
                eventos.put(evento.getId(), evento.getId());
            }
            retorno.put("eventos", eventos);
        }

        final HashMap<String, Object>eventosCreados= new HashMap<String,Object>();
        if(this.eventosCreados!=null) {
            for (Evento evento : this.eventosCreados) {
                eventosCreados.put(evento.getId(), evento.getId());
            }
            retorno.put("eventosCreados", eventosCreados);
        }

        if(ubicacionActual!=null)
            retorno.put("ubicacionActual",ubicacionActual.getId());

        retorno.put("correo",super.getCorreo());
        retorno.put("nombreUsuario", super.getNombreUsuario());
        retorno.put("sexo",super.getSexo());
        //private String id;
        Almacenamiento almacenamiento=new Almacenamiento();
        //almacenamiento.push(retorno, "Jugador/",this.getId());///Si da error aca es por que no asignaron el id del usuario a la clase

    }

}

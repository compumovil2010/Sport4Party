package com.example.sport4party.Modelo;

import com.example.sport4party.Utils.Almacenamiento;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Mensaje implements Serializable {
    private String id=null;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public void pushFireBaseBD()
    {
        final HashMap<String, Object> retorno= new HashMap<String,Object>();
        retorno.put("contenido", this.contenido);
        retorno.put("fecha",this.fecha);
        retorno.put("remitente", this.remitente.getId());
        Almacenamiento almacenamiento=new Almacenamiento();
        if(this.id==null)
        {
            this.id=almacenamiento.push(retorno, "Mensaje/");
        }
        else
        {
            almacenamiento.push(retorno,"Mensaje/",this.getId());
        }

    }
}

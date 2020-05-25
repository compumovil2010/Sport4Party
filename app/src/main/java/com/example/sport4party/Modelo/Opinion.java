package com.example.sport4party.Modelo;
import com.example.sport4party.Utils.Almacenamiento;

import java.io.Serializable;
import java.util.HashMap;

public class Opinion implements Serializable {
    String id=null;
    private Double calificacion;
    private String descripcion;
    //Relaciones
    private Ubicacion detalles;
    private Jugador remitente;


    //Constructor
    public Opinion(Double calificacion, String descripcion, Ubicacion detalles, Jugador remitente) {
        this.calificacion = calificacion;
        this.descripcion = descripcion;
        this.detalles = detalles;
        this.remitente = remitente;
    }
    public Opinion()
    {

    }

    //Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCalificacion() {
        return calificacion;
    }
    public void setCalificacion(Double calificacion) {
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

    public void pushFireBaseBD() {
        final HashMap<String, Object> retorno = new HashMap<String, Object>();
        retorno.put("calificacion",this.calificacion);
        retorno.put("descripcion",this.descripcion);
        retorno.put("detalles",this.detalles.getId());
        retorno.put("remitente",this.remitente.getId());
        Almacenamiento almacenamiento=new Almacenamiento();
        if(this.id==null) {
            this.setId(almacenamiento.push(retorno, "Opinion/"));
        }
        else{
            almacenamiento.push(retorno,"Opinion/", this.id);
        }
    }

}
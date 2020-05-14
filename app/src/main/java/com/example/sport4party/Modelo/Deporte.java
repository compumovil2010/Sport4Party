package com.example.sport4party.Modelo;

import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.HashMap;

public class Deporte implements Serializable {
    private String id=null;
    private Integer cantidadJugadores;
    private String nombre;

    //Constructor
    public Deporte(Integer cantidadJugadores, String nombre) {
        this.cantidadJugadores = cantidadJugadores;
        this.nombre = nombre;
    }
    public Deporte()
    {

    }

    //Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public void pushFireBaseBD()
    {
        Almacenamiento almacenamiento=new Almacenamiento();
        HashMap<String, Object> retorno= new HashMap<String,Object>();
        retorno.put("cantidadJugadores", this.cantidadJugadores);
        retorno.put("nombre",this.getNombre());
        if(this.getId()==null)
        {
            this.setId(almacenamiento.push(retorno, "Deporte/"));
        }
        else
        {
            almacenamiento.push(retorno, "Deporte/",this.getId());
        }

    }
    public void fromHashMapToObject(HashMap<String, Object> retorno, String key)
    {
        this.setId(key);
        this.setCantidadJugadores(Integer.parseInt((String)retorno.get("cantidadJugadores")));
        this.setNombre((String) retorno.get("nombre"));
    }
}

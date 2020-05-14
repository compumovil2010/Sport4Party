package com.example.sport4party.Modelo;

import android.util.Log;
import android.widget.Toast;

import com.example.sport4party.Mapa;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ubicacion implements Serializable {
    private String id=null;
    private String descripcion;
    private Double latitud;
    private Double Longitud;
    private boolean valida;
    //Relaciones
    private List<Deporte> deportesDisponibles;

    //Constructor
    public Ubicacion()
    {

    }
    public Ubicacion(String descripcion, Date horario, Double latitud, Double longitud, boolean valida) {
        this.descripcion = descripcion;
        this.latitud = latitud;
        Longitud = longitud;
        this.valida = valida;
    }
    public Ubicacion(String descripcion, Date horario, Double latitud, Double longitud, boolean valida, List<Deporte> deportesDisponibles) {
        this.descripcion = descripcion;
        this.latitud = latitud;
        Longitud = longitud;
        this.valida = valida;
        this.deportesDisponibles = deportesDisponibles;
    }

    //Adders
    public void addDeporte(Deporte deporte){
        deportesDisponibles.add(deporte);
    }

    //Getters and setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Double getLatitud() {
        return latitud;
    }
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
    public Double getLongitud() {
        return Longitud;
    }
    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }
    public boolean isValida() {
        return valida;
    }
    public void setValida(boolean valida) {
        this.valida = valida;
    }
    public List<Deporte> getDeportesDisponibles() {
        return deportesDisponibles;
    }
    public void setDeportesDisponibles(List<Deporte> deportesDisponibles) {
        this.deportesDisponibles = deportesDisponibles;
    }
    public void pushFireBaseBD()
    {
        final HashMap<String, Object>retorno= new HashMap<String,Object>();
        retorno.put("descripcion", this.getDescripcion());
        retorno.put("latitud",this.getLatitud());
        retorno.put("Longitud", this.Longitud);
        retorno.put("valida",this.valida);
        final HashMap<String,Object> deportesDisponiblesMapa=new HashMap<String, Object>();

        for (Deporte deporte: deportesDisponibles) {
            deportesDisponiblesMapa.put(deporte.getId(), deporte.getId());
        }
        retorno.put("deportesDisponibles", deportesDisponiblesMapa);
        Almacenamiento almacenamiento=new Almacenamiento();
        if(this.id==null) {
            this.setId(almacenamiento.push(retorno, "Ubicacion/"));
        }
        else
        {
            almacenamiento.push(retorno,"Ubicacion/",this.id);
        }
    }

    public void fromHashMapToObject(HashMap<String, Object> retorno, String key)
    {
        //this.setDeportesDisponibles();
        this.setDescripcion((String) retorno.get("descripcion" ));
        this.setId(key);
        this.setLatitud(Double.parseDouble((String)retorno.get("latitud")));
        this.setLongitud(Double.parseDouble((String)retorno.get("Longitud")));
        this.setValida(Boolean.parseBoolean((String)retorno.get("valida")));

        final HashMap<String, Object>deportesDisponiblesMapa= (HashMap<String,Object>)retorno.get("deportesDisponibles");
        deportesDisponibles=new ArrayList<Deporte>();
        for (String mapa : deportesDisponiblesMapa.keySet())
        {
            Almacenamiento buscar= new Almacenamiento(){
                @Override
                public void onBuscarResult(HashMap<String, Object> data, String key) {
                    Deporte aux=new Deporte();
                    aux.fromHashMapToObject(data,key);
                    deportesDisponibles.add(aux);
                }
            };
            buscar.buscarPorID("Deporte/", mapa);
        }
    }
    public void inicializarConId(String key)
    {
        Almacenamiento buscar= new Almacenamiento(){
            @Override
            public void onBuscarResult(HashMap<String, Object> data, String key) {
                fromHashMapToObject(data,key);
            }
        };
        buscar.buscarPorID("Evento/",key);
    }
}

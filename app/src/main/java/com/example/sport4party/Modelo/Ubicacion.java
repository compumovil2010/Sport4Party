package com.example.sport4party.Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Ubicacion implements Serializable {

    private String descripcion;
    private Double latitud;
    private Double Longitud;
    private boolean valida;
    //Relaciones
    private List<Deporte> deportesDisponibles;

    //Constructor
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
}

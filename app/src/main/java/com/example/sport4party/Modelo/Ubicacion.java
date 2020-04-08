package com.example.sport4party.Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Ubicacion implements Serializable {

    private String descripcion;
    private Date horario;
    private Long latitud;
    private Long Longitud;
    private boolean valida;
    //Relaciones
    private List<Deporte> deportesDisponibles;

    //Constructor
    public Ubicacion(String descripcion, Date horario, Long latitud, Long longitud, boolean valida) {
        this.descripcion = descripcion;
        this.horario = horario;
        this.latitud = latitud;
        Longitud = longitud;
        this.valida = valida;
    }
    public Ubicacion(String descripcion, Date horario, Long latitud, Long longitud, boolean valida, List<Deporte> deportesDisponibles) {
        this.descripcion = descripcion;
        this.horario = horario;
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
    public Date getHorario() {
        return horario;
    }
    public void setHorario(Date horario) {
        this.horario = horario;
    }
    public Long getLatitud() {
        return latitud;
    }
    public void setLatitud(Long latitud) {
        this.latitud = latitud;
    }
    public Long getLongitud() {
        return Longitud;
    }
    public void setLongitud(Long longitud) {
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

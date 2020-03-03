package com.example.sport4party.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Evento implements Serializable {
    private int cupo;
    private String deporte;
    private Date fecha;
    private int ID;
    private String nivelHabilidad;
    private boolean pago;

    public Evento(int cupo, String deporte, Date fecha, int ID, String nivelHabilidad, boolean pago) {
        this.cupo = cupo;
        this.deporte = deporte;
        this.fecha = fecha;
        this.ID = ID;
        this.nivelHabilidad = nivelHabilidad;
        this.pago = pago;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getID() {
        return ID;
    }

    public String getNivelHabilidad() {
        return nivelHabilidad;
    }

    public void setNivelHabilidad(String nivelHabilidad) {
        this.nivelHabilidad = nivelHabilidad;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }
}

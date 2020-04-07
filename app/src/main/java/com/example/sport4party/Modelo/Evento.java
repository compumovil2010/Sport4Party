package com.example.sport4party.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Evento implements Serializable {

    private int ID;
    private String descripcion;
    private Date fecha;
    private String nivelHabilidad;
    private String nombre;
    private String precio;
    private boolean pago;
    //relaciones
    private Deporte deporte;
    private Ubicacion ubicacion;


    /*public Evento(int cupo, String deporte, Date fecha, int ID, String nivelHabilidad, boolean pago, boolean nTipo) {
        this.cupo = cupo;
        this.deporte = deporte;
        this.fecha = fecha;
        this.ID = ID;
        this.nivelHabilidad = nivelHabilidad;
        this.pago = pago;
        this.privado = nTipo;
    }*/

    public int getCupo() {
        return 0;
        //return cupo;
    }

    public void setCupo(int cupo) {
        //this.cupo = cupo;
    }

    public String getDeporte() {
        return "";
        //return deporte;
    }

    public void setDeporte(String deporte) {
        //this.deporte = deporte;
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

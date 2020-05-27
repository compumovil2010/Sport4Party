package com.example.sport4party.Modelo;

import com.example.sport4party.Utils.Almacenamiento;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class Evento implements Serializable {

    private int ID;
    private String id=null;
    private String descripcion;
    private Date fecha;
    private String nivelHabilidad;
    private String nombre;
    private String precio;
    private boolean pago;
    private boolean privado;
    private long cupos;
    //relaciones
    private Deporte deporte;
    private Ubicacion ubicacion;

    //Constructor
    public Evento()
    {


    }

    public Evento(int ID, String descripcion,int cupos, Date fecha, String nivelHabilidad, String nombre, String precio, boolean pago, boolean privado, Deporte deporte, Ubicacion ubicacion) {
        this.ID = ID;
        this.cupos=cupos;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.nivelHabilidad = nivelHabilidad;
        this.nombre = nombre;
        this.precio = precio;
        this.pago = pago;
        this.privado = privado;
        this.deporte = deporte;
        this.ubicacion = ubicacion;
    }

    //Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getNivelHabilidad() {
        return nivelHabilidad;
    }
    public void setNivelHabilidad(String nivelHabilidad) {
        this.nivelHabilidad = nivelHabilidad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public boolean isPago() {
        return pago;
    }
    public void setPago(boolean pago) {
        this.pago = pago;
    }
    public boolean isPrivado() {
        return privado;
    }
    public void setPrivado(boolean privado) {
        this.privado = privado;
    }
    public Deporte getDeporte() {
        return deporte;
    }
    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    public long getCupos() {
        return cupos;
    }

    public void setCupos(long cupos) {
        this.cupos = cupos;
    }

    public static class NombreSorter implements Comparator<Evento> {
        @Override
        public int compare(Evento o1, Evento o2) { return o1.getNombre().compareTo(o2.getNombre() );
        }};
    public static class DeporteSorter implements Comparator<Evento> {
        @Override
        public int compare(Evento o1, Evento o2) { return o1.getDeporte().getNombre().compareTo(o2.getDeporte().getNombre() );
        }};
    public static class FechaSorter implements Comparator<Evento> {
        @Override
        public int compare(Evento o1, Evento o2) { return o1.getFecha().compareTo(o2.getFecha() );
        }};
    public static class PagoSorter implements Comparator<Evento> {
        @Override
        public int compare(Evento o1, Evento o2) { return o1.getPrecio().compareTo(o2.getPrecio() );
        }};
    public void pushFireBaseBD()
    {
        final HashMap<String, Object> retorno= new HashMap<String,Object>();
        retorno.put("ID", this.getID());
        retorno.put("descripcion",this.descripcion);
        retorno.put("fecha", this.fecha);
        retorno.put("nivelHabilidad",this.nivelHabilidad);
        retorno.put("nombre",this.nombre);
        retorno.put("precio",this.precio);
        retorno.put("pago",this.pago);
        retorno.put("privado",this.privado);
        retorno.put("cupos",this.cupos);
        if(this.deporte!=null)
        retorno.put("deporte",this.deporte.getId());

        if(this.ubicacion!=null)
        retorno.put("ubicacion", this.ubicacion.getId());

        Almacenamiento almacenamiento=new Almacenamiento();
        if(this.id==null)
        {
            this.id=almacenamiento.push(retorno, "Evento/");
        }
        else
        {
            almacenamiento.push(retorno,"Evento/",this.getId());
        }

    }

}

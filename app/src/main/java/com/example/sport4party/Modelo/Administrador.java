package com.example.sport4party.Modelo;

import android.graphics.Bitmap;

import java.util.List;

public class Administrador extends Usuario {

    private List<Ubicacion> validacionPendiente;


    //Constructores
    public Administrador(String contraseña, String correo, String nombreUsuario, String sexo) {
        super(contraseña, correo, nombreUsuario, sexo);
    }
    public Administrador(String contraseña, String correo, Bitmap imagenPerfil, String nombreUsuario, String sexo) {
        super(contraseña, correo, imagenPerfil, nombreUsuario, sexo);
    }

    //adders
    public void addUbicacion(Ubicacion ubicacion) {
        validacionPendiente.add(ubicacion);
    }

    //getters and setters
    public List<Ubicacion> getValidacionPendiente() {
        return validacionPendiente;
    }
    public void setValidacionPendiente(List<Ubicacion> validacionPendiente) {
        this.validacionPendiente = validacionPendiente;
    }

    //Funtions

}

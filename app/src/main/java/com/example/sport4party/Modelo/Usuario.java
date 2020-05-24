package com.example.sport4party.Modelo;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String contraseña;
    private String correo;
    private String imagenPerfil;
    private String nombreUsuario;
    private String sexo;
    private String tipo;

    //Constructores
    public Usuario(){

    }

    public Usuario(String correo, String nombreUsuario, String sexo, String tipo){
        this.contraseña = null;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.sexo = sexo;
        this.imagenPerfil = null;
        this.tipo = tipo;
    }

    public Usuario(String contraseña, String correo, String imagenPerfil, String nombreUsuario, String sexo) {
        this.contraseña = contraseña;
        this.correo = correo;
        this.imagenPerfil = imagenPerfil;
        this.nombreUsuario = nombreUsuario;
        this.sexo = sexo;
    }

    //Getters and Setters
    public String getContraseña() {
        return contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}

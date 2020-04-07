package com.example.sport4party.Modelo;

import java.io.Serializable;

public class Opinion implements Serializable {

    private Float calificacion;
    private String descripcion;

    //Relaciones
    private Ubicacion detalles;
    private Jugador remitente;
}

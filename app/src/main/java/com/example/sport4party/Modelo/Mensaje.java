package com.example.sport4party.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Mensaje implements Serializable {

    private String contenido;
    private Date fecha;

    //relaciones
    private Jugador remitente;
}

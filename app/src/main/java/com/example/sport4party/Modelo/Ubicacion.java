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

    //funciones
}

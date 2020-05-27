package com.example.sport4party;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class JugadorAdapter extends BaseAdapter {

    private Context aContext;
    //Lista de jugadores que maneja el adaptador
    private ArrayList<Jugador> deports;
    //Boolean para indicar si se va a desplegar en pantalla de Ver participantes
    private boolean enParticipantes;
    //Boolean para indicar si se va a desplegar en pantalla de Invitar amigos
    private boolean invitarAmigos;
    //Perfil del usuario que esta usando la aplicacion
    private Jugador perfilApp;
    //Evento sobre el cual se va a verficar (unicamnete necesario en InvitarAmigos)
    private Evento eventoAct;
    //Cantidad de gente inscrita
    private int inscritos = 0;
    private String rutaJugadores = "Jugador/";

    public JugadorAdapter(Context nContext, ArrayList<Jugador>nDeports, boolean nparticipantes, boolean ninvitar, Jugador nPerfil, Evento nEvent, int nInsc){
        this.aContext = nContext;
        this.deports = nDeports;
        this.enParticipantes = nparticipantes;
        this.invitarAmigos = ninvitar;
        this.perfilApp = nPerfil;
        this.eventoAct = nEvent;
        this.inscritos = nInsc;
    }

    @Override
    public int getCount() {
        return deports.size();
    }

    @Override
    public Object getItem(int position) {
        return deports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vista = View.inflate(aContext, R.layout.casilla_participante,null);

        TextView userName = vista.findViewById(R.id.userName);
        TextView correo = vista.findViewById(R.id.lastTimeCon);
        final Button add = vista.findViewById(R.id.addButton);

        userName.setText(deports.get(position).getNombreUsuario());
        correo.setText(deports.get(position).getCorreo());

        if(enParticipantes){
            if(esAmigo(deports.get(position))){
                add.setText("Ya eres amigo de este usuario");
                add.setBackgroundResource(R.drawable.boton_general);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(v.getContext(),"Este participante es tu amigo",Toast.LENGTH_LONG).show();
                    }
                });
            }else if(deports.get(position).getId().equals(perfilApp.getId())){
                add.setText("Este eres tu");
                add.setBackgroundResource(R.drawable.boton_general2);
                add.setClickable(false);
            }
            else{
                add.setText("Agregar a mis amigos");
                add.setBackgroundResource(R.drawable.boton_general3);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        agregarAMisAmigos(deports.get(position));
                        //Toast.makeText(v.getContext(),"Participante agregado a la lista de amigos",Toast.LENGTH_LONG).show();
                        //agregarAMisAmigos(deports.get(position));
                        add.setText("Ya eres amigo de este usuario");
                        add.setBackgroundResource(R.drawable.boton_general);
                    }
                });
            }
        }else if(invitarAmigos){
            if(estaEnEvento(deports.get(position))){
                add.setText("Ya esta en el evento");
                add.setBackgroundResource(R.drawable.boton_general2);
            }else{
                add.setText("Invitar");
                add.setBackgroundResource(R.drawable.boton_general);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Cupos totales: "+eventoAct.getCupos());
                        System.out.println("Inscritos: "+inscritos);
                        if(inscritos + 1 <= eventoAct.getCupos()){
                        agregarAmigoAEvento(deports.get(position));
                        Toast.makeText(v.getContext(),"Amigo agregado al evento",Toast.LENGTH_LONG).show();
                        add.setText("Agregado");
                        add.setBackgroundResource(R.drawable.boton_general2);
                        add.setClickable(false);
                        //}else{
                        //Toast.makeText(v.getContext(),"No se pueden agregar mas personas",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(v.getContext(),"No se pueden agregar mas personas",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        }else if(!enParticipantes && !invitarAmigos){
            add.setVisibility(View.INVISIBLE);
            add.setClickable(false);
        }

        return vista;
    }

    boolean esAmigo(Jugador aBuscar){
        if(perfilApp==null){
            return false;
        }else{
            for (Jugador j: perfilApp.getAmigos()) {
                System.out.println(j.getNombreUsuario()+"es amigo de "+perfilApp.getNombreUsuario());
                if(aBuscar.getId().equals(j.getId())){
                    System.out.println("si lo encontre como amigo");
                    return true;
                }
            }
            return false;
        }
    }

    boolean estaEnEvento(Jugador aBuscar){
        if(eventoAct != null){
            System.out.println("Hay evento");
            for (Evento e: aBuscar.getEventos()) {
                System.out.println("id evento"+ eventoAct.getId());
                System.out.println("id a ver"+e.getId());
                if(eventoAct.getId().equals(e.getId())){
                    System.out.println(aBuscar.getNombreUsuario()+" fue encontrado xD");
                    return true;
                }
            }
        }else{
            System.out.println("no hay evento jaja");
            return false;
        }

        return false;
    }

    void agregarAMisAmigos(Jugador aAgregar){
        boolean yaEsta=false;
        System.out.println("Voy a agregar desde le adapter");
        System.out.println(perfilApp.getNombreUsuario()+": "+perfilApp.getId());
        System.out.println(rutaJugadores+perfilApp.getId());
        System.out.println(aAgregar.getNombreUsuario()+": "+aAgregar.getId());
        System.out.println(rutaJugadores+aAgregar.getId());
        Almacenamiento almacenamiento = new Almacenamiento();
        almacenamiento.push(aAgregar.getId(), "Jugador/" + perfilApp.getId() + "/amigos/", aAgregar.getId());
        almacenamiento.push(perfilApp.getId(), "Jugador/" + aAgregar.getId() + "/amigos/", perfilApp.getId());
        for (Jugador j: perfilApp.getAmigos()) {
            if(j.getId().equals(aAgregar.getId())){
                yaEsta = true;
            }
        }
        if(!yaEsta){
            perfilApp.getAmigos().add(aAgregar);
        }

    }

    void agregarAmigoAEvento(Jugador aAgregar){
        boolean inscrito = false;
        boolean propio = false;
        for (Evento e: aAgregar.getEventos()) {
            if(e.getId().equals(eventoAct.getId())){
                inscrito = true;
            }
        }
        if(!inscrito){
            for (Evento e: aAgregar.getEventosCreados()) {
                if(e.getId().equals(eventoAct.getId())){
                    propio = true;
                }
            }
        }
        if(!inscrito && !propio){
            Almacenamiento ala = new Almacenamiento();
            ala.push(eventoAct.getId(),"Jugador/"+aAgregar.getId()+"/eventos/",eventoAct.getId());
        }
    }


}

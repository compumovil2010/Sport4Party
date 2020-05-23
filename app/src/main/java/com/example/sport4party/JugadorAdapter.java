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

    public JugadorAdapter(Context nContext, ArrayList<Jugador>nDeports, boolean nparticipantes, boolean ninvitar, Jugador nPerfil, Evento nEvent){
        this.aContext = nContext;
        this.deports = nDeports;
        this.enParticipantes = nparticipantes;
        this.invitarAmigos = ninvitar;
        this.perfilApp = nPerfil;
        this.eventoAct = nEvent;
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
        TextView lastTime = vista.findViewById(R.id.lastTimeCon);
        final Button add = vista.findViewById(R.id.addButton);

        userName.setText(deports.get(position).getNombreUsuario());
        lastTime.setText(deports.get(position).getCorreo());

        if(enParticipantes){
            if(esAmigo(deports.get(position))){
                add.setText("Ya eres amigo de este usuario");
                add.setBackgroundResource(R.drawable.boton_general);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(),"Este participante es tu amigo",Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                add.setText("Agregar a mis amigos");
                add.setBackgroundResource(R.drawable.boton_general3);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(),"Participante agregado a la lista de amigos",Toast.LENGTH_LONG).show();
                        //agregarAMisAmigos(deports.get(position));
                        add.setText("Ya eres amigo de este usuario");
                        add.setBackgroundResource(R.drawable.boton_general);
                    }
                });
            }
        }else if(invitarAmigos){
            add.setText("Invitar");
            add.setBackgroundResource(R.drawable.boton_general);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(cantidadDeGenteInscrita + 1 <= eventoAct.getCupos()){
                        //agregarAmigoAEvento(deports.get(position));
                        Toast.makeText(v.getContext(),"Amigo agregado al evento",Toast.LENGTH_LONG).show();
                        add.setText("Agregado");
                        add.setBackgroundResource(R.drawable.boton_general2);
                        add.setClickable(false);
                    //}else{
                        //Toast.makeText(v.getContext(),"No se pueden agregar mas personas",Toast.LENGTH_LONG).show();
                    //}
                }
            });
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
            return perfilApp.getAmigos().contains(aBuscar);
        }
    }

    void agregarAMisAmigos(Jugador aAgregar){
        perfilApp.getAmigos().add(aAgregar);
        perfilApp.pushFireBaseBD();
    }

    void agregarAmigoAEvento(Jugador aAgregar){
        aAgregar.addEventos(eventoAct);
        aAgregar.pushFireBaseBD();
        eventoAct.setCupos(eventoAct.getCupos()+1);
        eventoAct.pushFireBaseBD();
    }


}

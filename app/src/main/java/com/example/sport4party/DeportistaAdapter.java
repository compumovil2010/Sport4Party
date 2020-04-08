package com.example.sport4party;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sport4party.Modelo.Deportista;

import java.util.ArrayList;


public class DeportistaAdapter extends BaseAdapter {

    private Context aContext;
    //Lista de deportistas que maneja el adaptador
    private ArrayList<Deportista> deports;
    //Boolean para indicar si se va a desplegar en pantalla de Ver participantes
    private boolean enParticipantes;
    //Boolena para indicar si se va a desplegar en pantalla de Invitar amigos
    private boolean invitarAmigos;

    public DeportistaAdapter(Context nContext, ArrayList<Deportista>nDeports, boolean nparticipantes, boolean ninvitar){
        this.aContext = nContext;
        this.deports = nDeports;
        this.enParticipantes = nparticipantes;
        this.invitarAmigos = ninvitar;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = View.inflate(aContext, R.layout.casilla_participante,null);

        TextView userName = vista.findViewById(R.id.userName);
        TextView lastTime = vista.findViewById(R.id.lastTimeCon);
        Button add = vista.findViewById(R.id.addButton);

        userName.setText(deports.get(position).getNombre());
        lastTime.setText("Ultima vez conectado \n 02/04/2020");

        if(enParticipantes){
            add.setText("Agregar a mis amigos");
            add.setBackgroundResource(R.drawable.boton_general3);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Participante agregado a la lista de amigos",Toast.LENGTH_LONG).show();
                }
            });

        }else if(invitarAmigos){
            add.setText("Invitar");
            add.setBackgroundResource(R.drawable.boton_general);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Amigo agregado al evento",Toast.LENGTH_LONG).show();
                }
            });
        }else if(!enParticipantes && !invitarAmigos){
            add.setVisibility(View.INVISIBLE);
            add.setClickable(false);
        }

        return vista;
    }


}

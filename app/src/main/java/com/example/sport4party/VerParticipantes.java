package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sport4party.Modelo.Jugador;

import java.io.Serializable;
import java.util.ArrayList;

public class VerParticipantes extends AppCompatActivity {

    Button invitarAmigos;
    Button localizar;
    ListView listaAsistentes;
    JugadorAdapter adapter;
    ArrayList<Jugador> participantes;
    Jugador origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_participantes);

        invitarAmigos = findViewById(R.id.botonInvitarAmigos);
        localizar = findViewById(R.id.botonLocalizar);
        listaAsistentes = findViewById(R.id.listViewParticipantes);

        //Extraigo el Bundle con la información de los participantes del envento
        final Bundle info = getIntent().getBundleExtra("listaParticipantes");

        //Asigno la lista de participantes al ArrayList que va a manejar el adaptador
        participantes = (ArrayList<Jugador>) info.getSerializable("participantes");

        //La idea es que este sea el perfil que se maneja en la aplicacion
        origin = new Jugador("puf","asb", "asb", "Mael", "masculino");
        origin.addAmigos(participantes.get(0));
        origin.addAmigos(participantes.get(3));
        origin.addAmigos(participantes.get(6));
        adapter = new JugadorAdapter(getApplicationContext(), participantes,true, false, origin);
        listaAsistentes.setAdapter(adapter);

        invitarAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInvitarAmigos = new Intent(v.getContext(), InvitarAmigos.class);
                Bundle extaInfo = new Bundle();
                //Se debería incluir la capacidad máxima del evento, con el fin de que al agregar no se pase del límite establecido
                //Amigos debería ser la lista de Jugador que tiene el usuario actual
                extaInfo.putSerializable("amigos", (Serializable) participantes);
                extaInfo.putSerializable("perfilActual",(Serializable) origin);
                intentInvitarAmigos.putExtra("listaDeAmigos",extaInfo);
                startActivity(intentInvitarAmigos);
            }
        });

        listaAsistentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                Jugador perfil = participantes.get(position);
                infoPerfil.putExtra("jugador", perfil);
                if(origin.getAmigos().contains(perfil))
                    infoPerfil.putExtra("tipo", "1");
                else
                    infoPerfil.putExtra("tipo", "2");
                startActivity(infoPerfil);
            }
        });
    }
}

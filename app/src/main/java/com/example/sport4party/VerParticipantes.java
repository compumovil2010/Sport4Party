package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sport4party.Modelo.Deportista;
import com.example.sport4party.Modelo.Evento;

import java.io.Serializable;
import java.util.ArrayList;

public class VerParticipantes extends AppCompatActivity {

    Button invitarAmigos;
    ListView listaAsistentes;
    DeportistaAdapter adapter;
    ArrayList<Deportista> participantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_participantes);

        invitarAmigos = findViewById(R.id.botonanadiramigos);
        listaAsistentes = findViewById(R.id.listaparticipantes);

        //Este método es para cambiar el nombre en la barra de acción para que no quede el nombre del archivo
        getSupportActionBar().setTitle("Participantes del evento");
        //Activo la barra para volver hacia atrás que ofrece android
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //Extraigo el Bundle con la información de los participantes del envento
        final Bundle info = getIntent().getBundleExtra("listaParticipantes");

        //Asigno la lista de participantes al ArrayList que va a manejar el adaptador
        participantes = (ArrayList<Deportista>) info.getSerializable("participantes");


        adapter = new DeportistaAdapter(getApplicationContext(), participantes,true, false);
        listaAsistentes.setAdapter(adapter);

        invitarAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInvitarAmigos = new Intent(v.getContext(), InvitarAmigos.class);
                Bundle extaInfo = new Bundle();
                //Se debería incluir la capacidad máxima del evento, con el fin de que al agregar no se pase del límite establecido
                //Amigos debería ser la lista de Deportistas que tiene el usuario actual
                extaInfo.putSerializable("amigos", (Serializable) participantes);
                intentInvitarAmigos.putExtra("listaDeAmigos",extaInfo);
                startActivity(intentInvitarAmigos);
            }
        });

        listaAsistentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                Deportista miPerfil = participantes.get(position);
                infoPerfil.putExtra("deportista", miPerfil);
                infoPerfil.putExtra("tipo", "1");
                startActivity(infoPerfil);
            }
        });
    }
}

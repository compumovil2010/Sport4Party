package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sport4party.Modelo.Jugador;

import java.util.ArrayList;

public class InvitarAmigos extends AppCompatActivity {

    ListView inivitarAmigos;
    ArrayList<Jugador> friends;
    JugadorAdapter adapter;
    Jugador origin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitar_amigos);


        inivitarAmigos = findViewById(R.id.listaamigos);

        //Extraigo el Bundle con la información de los amigos del usuario actual
        Bundle bundleInfo = getIntent().getBundleExtra("listaDeAmigos");

        //Asigno la lista de amigos al ArrayList que va a manejar el adaptador
        friends = (ArrayList<Jugador>) bundleInfo.getSerializable("amigos");
        origin = (Jugador) bundleInfo.getSerializable("perfilActual");

        adapter = new JugadorAdapter(getApplicationContext(), friends, false, true, origin);
        inivitarAmigos.setAdapter(adapter);

        //Funcionalidad que debe "evitar" que el usuario añada a amigos que ya estén inscritos en el evento
        //Utilizar la información del límite de personas para el evento, con tal de no añadir de más

        inivitarAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                Jugador miPerfil = friends.get(position);
                infoPerfil.putExtra("jugador", miPerfil);
                infoPerfil.putExtra("tipo", "1");
                startActivity(infoPerfil);
            }
        });

    }
}

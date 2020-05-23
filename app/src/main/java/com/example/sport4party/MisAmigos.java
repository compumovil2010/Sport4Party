package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sport4party.Modelo.Jugador;

import java.util.ArrayList;

public class MisAmigos extends AppCompatActivity {

    ListView misAmigos;
    JugadorAdapter adapter;
    ArrayList<Jugador> amigos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_amigos);

        misAmigos = findViewById(R.id.listaamigos);


        //Extraigo el Bundle con la información de los amigos del usuario actual
        Bundle info = getIntent().getBundleExtra("listaAmigos");
        //Asigno la lista de amigos al ArrayList que va a manejar el adaptador
        amigos = (ArrayList<Jugador>) info.getSerializable("amigos");

        //La idea es que este sea el perfil que se maneja en la aplicacion
        Jugador origin = new Jugador("po","asb", "asb", "Mael", "masculino");
        adapter = new JugadorAdapter(this, amigos, false, false, origin, null);
        misAmigos.setAdapter(adapter);

        misAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                Jugador miPerfil = amigos.get(position);
                infoPerfil.putExtra("jugador", miPerfil);
                infoPerfil.putExtra("tipo", "1");
                startActivity(infoPerfil);
            }
        });
    }
}

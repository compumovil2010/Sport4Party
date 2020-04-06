package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sport4party.Modelo.Deportista;

import java.util.ArrayList;

public class MisAmigos extends AppCompatActivity {

    ListView misAmigos;
    DeportistaAdapter adapter;
    ArrayList<Deportista> amigos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_amigos);

        misAmigos = findViewById(R.id.Amigos);


        //Extraigo el Bundle con la información de los amigos del usuario actual
        Bundle info = getIntent().getBundleExtra("listaAmigos");
        //Asigno la lista de amigos al ArrayList que va a manejar el adaptador
        amigos = (ArrayList<Deportista>) info.getSerializable("amigos");

        adapter = new DeportistaAdapter(this, amigos, false, false);
        misAmigos.setAdapter(adapter);

        misAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                Deportista miPerfil = amigos.get(position);
                infoPerfil.putExtra("deportista", miPerfil);
                infoPerfil.putExtra("tipo", "1");
                startActivity(infoPerfil);
            }
        });
    }
}

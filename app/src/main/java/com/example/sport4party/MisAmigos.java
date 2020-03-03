package com.example.sport4party;

import android.os.Bundle;
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

        //Este método es para cambiar el nombre en la barra de acción para que no quede el nombre del archivo
        getSupportActionBar().setTitle("Mis amigos");
        //Activo la barra para volver hacia atrás que ofrece android
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Extraigo el Bundle con la información de los amigos del usuario actual
        Bundle info = getIntent().getBundleExtra("listaAmigos");
        //Asigno la lista de amigos al ArrayList que va a manejar el adaptador
        amigos = (ArrayList<Deportista>) info.getSerializable("amigos");

        adapter = new DeportistaAdapter(this, amigos, false, false);
        misAmigos.setAdapter(adapter);

    }
}

package com.example.sport4party;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sport4party.Modelo.Deportista;

import java.util.ArrayList;

public class InvitarAmigos extends AppCompatActivity {

    ListView inivitarAmigos;
    ArrayList<Deportista> friends;
    DeportistaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitar_amigos);

        //Este método es para cambiar el nombre en la barra de acción para que no quede el nombre del archivo
        getSupportActionBar().setTitle("Invitar amigos");
        //Activo la barra para volver hacia atrás que ofrece android (por ahora queda en false por temas de onCreate de a donde vuelve)
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        inivitarAmigos = findViewById(R.id.listaamigos);

        //Extraigo el Bundle con la información de los amigos del usuario actual
        Bundle bundleInfo = getIntent().getBundleExtra("listaDeAmigos");

        //Asigno la lista de amigos al ArrayList que va a manejar el adaptador
        friends = (ArrayList<Deportista>) bundleInfo.getSerializable("amigos");

        adapter = new DeportistaAdapter(getApplicationContext(), friends, false, true);
        inivitarAmigos.setAdapter(adapter);

        //Funcionalidad que debe "evitar" que el usuario añada a amigos que ya estén inscritos en el evento
        //Utilizar la información del límite de personas para el evento, con tal de no añadir de más

    }
}

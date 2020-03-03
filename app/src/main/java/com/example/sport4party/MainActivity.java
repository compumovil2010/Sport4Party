package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button accionParticipantes;
    Button verAmigos;
    ArrayList<Deportista> deportistas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accionParticipantes = findViewById(R.id.botonparticipantes);
        verAmigos = findViewById(R.id.botonVerAmigos);

        getSupportActionBar().setTitle("Información del evento");


        deportistas = new ArrayList<>();
        deportistas.add(new Deportista(1,"Juan Francisco Hamon", "Bueno", 4f));
        deportistas.add(new Deportista(2,"Diego Barajas", "Regular", 3f));
        deportistas.add(new Deportista(3,"Brandonn Cruz", "Malo", 2f));
        deportistas.add(new Deportista(4,"Santiago Chaparro", "Bueno", 5f));
        deportistas.add(new Deportista(5,"Pedro Fernandez", "Bueno", 4f));
        deportistas.add(new Deportista(6,"Santiago Herrera", "Regular", 3f));
        deportistas.add(new Deportista(7,"Carlos Orduz", "Malo", 2f));
        deportistas.add(new Deportista(8,"Diego Ignacio Martinez", "Bueno", 5f));

        accionParticipantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verParticipantes = new Intent(v.getContext(), VerParticipantes.class);
                Bundle extaInfo = new Bundle();
                extaInfo.putSerializable("participantes", (Serializable) deportistas);
                verParticipantes.putExtra("listaParticipantes",extaInfo);
                startActivity(verParticipantes);
            }
        });

        verAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verAmigos = new Intent(v.getContext(),MisAmigos.class);
                Bundle extaInfo = new Bundle();
                extaInfo.putSerializable("amigos", (Serializable) deportistas);
                verAmigos.putExtra("listaAmigos",extaInfo);
                startActivity(verAmigos);
            }
        });
    }
}


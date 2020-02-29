package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sport4party.Modelo.Deportista;

public class Perfil extends AppCompatActivity {
    private Deportista perfil;
    private TextView nombreUsuario;
    private TextView nivel;
    private TextView amigos;
    private TextView eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent intent = getIntent();
        perfil = (Deportista)intent.getSerializableExtra("deportista");

        actualizar();
    }

    private void actualizar(){
        nombreUsuario = (TextView)findViewById(R.id.textViewNombreUsuario);
        nivel = (TextView)findViewById(R.id.textViewNivel);
        amigos = (TextView)findViewById(R.id.textViewAmigos);
        eventos = (TextView)findViewById(R.id.textViewEventos);

        nombreUsuario.setText(perfil.getNombre());
        nivel.setText(perfil.getNivelHabilidad());
        amigos.setText(Integer.toString(perfil.sizeAmigos()));
        eventos.append(" " + perfil.getNombre());
    }
}

package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InicioDeSesión extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);
    }

    public void toRegistro(View v){
        Intent change = new Intent(this, Registro.class);
        startActivity(change);
    }

    public void toMapa(View v){
        Intent change = new Intent(this, Mapa.class);
        startActivity(change);
    }
}

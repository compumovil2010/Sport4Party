package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sport4party.Modelo.Deportista;

public class MainActivity extends AppCompatActivity {
    Button button, button2;
    Deportista miPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMiPerfil();

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Perfil.class);
                intent.putExtra("deportista", miPerfil);
                intent.putExtra("tipo", "0");
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Perfil.class);
                intent.putExtra("deportista", miPerfil.getAmigos().get(0));
                intent.putExtra("tipo", "1");
                startActivity(intent);
            }
        });
    }

    private void initMiPerfil(){
        miPerfil = new Deportista(0, "Mael", "Bueno", 5);
        Deportista friend = new Deportista(1, "Luduciel", "Regular", 4);
        miPerfil.addAmigo(friend);
    }
}

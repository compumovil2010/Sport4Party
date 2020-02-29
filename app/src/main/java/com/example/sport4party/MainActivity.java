package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sport4party.Modelo.Deportista;

public class MainActivity extends AppCompatActivity {
    Button button;
    Deportista miPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMiPerfil();

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Perfil.class);
                intent.putExtra("deportista", miPerfil);
                startActivity(intent);
            }
        });
    }

    private void initMiPerfil(){
        miPerfil = new Deportista(0, "Mael", "Bueno", 5);
    }
}

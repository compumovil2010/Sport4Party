package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sport4party.Modelo.Jugador;

public class misEventos extends AppCompatActivity {
    private EventosAdapter eventosAdapter;
    private ListView listEventos;
    private Jugador perfil;
    private int tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);
        listEventos = (ListView)findViewById(R.id.listViewEventos);
        Intent intent = getIntent();
        perfil = (Jugador) intent.getSerializableExtra("jugador");
    }
    protected void onResume() {
        super.onResume();
        actualizar();
    }

    private void actualizar() {
                actualizarEventosUsuario();
    }
    private void actualizarEventosUsuario(){
        eventosAdapter = new EventosAdapter(this, perfil.getEventos(), true, false);
        listEventos.setAdapter(eventosAdapter);
    }

}

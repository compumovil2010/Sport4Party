package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sport4party.Modelo.Deportista;

public class misEventos extends AppCompatActivity {
    private EventosAdapter eventosAdapter;
    private ListView listEventos;
    private Deportista perfil;
    private int tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);
        listEventos = (ListView)findViewById(R.id.listViewEventos);
        Intent intent = getIntent();
        perfil = (Deportista) intent.getSerializableExtra("deportista");
    }
    protected void onResume() {
        super.onResume();
        actualizar();
    }

    private void actualizar() {
                actualizarEventosUsuario();
    }
    private void actualizarEventosUsuario(){
        eventosAdapter = new EventosAdapter(this, perfil.getEventos());
        listEventos.setAdapter(eventosAdapter);
    }

}

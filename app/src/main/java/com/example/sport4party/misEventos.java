package com.example.sport4party;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Jugador;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class misEventos extends AppCompatActivity {
    private EventosAdapter eventosAdapter;
    private ListView listEventos;
    private Jugador perfil;
    private int tipo;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);
        iniciarVista();
        Intent intent = getIntent();
        perfil = (Jugador) intent.getSerializableExtra("jugador");
        listEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent info = new Intent(view.getContext(), InformacionEvento.class);
                info.putExtra("pantalla", 0);
                startActivity(info);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        actualizar();
    }

    private void actualizar() {
        actualizarEventosUsuario();
    }

    private void actualizarEventosUsuario() {
        eventosAdapter = new EventosAdapter(this, perfil.getEventos(), true, false);
        listEventos.setAdapter(eventosAdapter);
    }

    private void iniciarVista() {
        listEventos = (ListView) findViewById(R.id.listViewEventos);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.inflateMenu(R.menu.miseventos_menu);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_tune_black_24dp);
        toolbar.setOverflowIcon(drawable);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.miseventos_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        List<Evento> misEventos = perfil.getEventos();
        int id = item.getItemId();
        if (id == R.id.menu_it_date) {
            Collections.sort(misEventos, new Evento.FechaSorter());
        } else if (id == R.id.menu_it_name) {
            Collections.sort(misEventos, new Evento.NombreSorter());
        } else if (id == R.id.menu_it_pay) {
            Collections.sort(misEventos, new Evento.PagoeSorter());
        } else if (id == R.id.menu_it_sport) {
            Collections.sort(misEventos, new Evento.DeporteSorter());
        }
        eventosAdapter = new EventosAdapter(this, misEventos, true, false);
        listEventos.setAdapter(eventosAdapter);
        return super.onOptionsItemSelected(item);
    }
}

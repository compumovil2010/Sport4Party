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


import com.example.sport4party.Modelo.Deporte;
import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MisEventos extends AppCompatActivity {
    private String perfilId;
    private ArrayList<String> idEventos;
    private EventosAdapter eventosAdapter;
    private ListView listEventos;
    private Jugador perfil;
    private int tipo;
    private Toolbar toolbar;
    private List<Evento> misEventos;
    //Autenticación
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);

        iniciarVista();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null)
            finish();

        perfil = new Jugador();
        perfil.setEventos(new ArrayList<Evento>());
        idEventos = new ArrayList<>();

        perfilId = mAuth.getCurrentUser().getUid();
        //perfilId = "1GWxKsBvBVZzXetfglHDPDZqXxj1";
        //perfilId = "uv8upyMvQIXL51Ci2dGArWWS1nc2";

        Almacenamiento almacenamiento = new Almacenamiento() {
            @Override
            public void leerDatosSubscrito(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                if (datos.containsKey("eventos")) {
                    DataSnapshot eventos = singleSnapShot.child("eventos/");
                    idEventos.clear();
                    perfil.setEventos(new ArrayList<Evento>());
                    for (DataSnapshot i : eventos.getChildren()) {
                        idEventos.add(i.getValue().toString());
                    }
                }
            }
        };
        almacenamiento.obtenerPorID("Jugador/", perfilId);

        Almacenamiento almacenamiento2 = new Almacenamiento() {
            @Override
            public void leerDatosSubscrito(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                String eventoId = singleSnapShot.getKey();
                if (idEventos.contains(eventoId)) {
                    perfil.addEventos(obtenerEvento(singleSnapShot));
                    idEventos.remove(eventoId);
                }
                actualizarEventosUsuario();
            }
        };
        almacenamiento2.loadSubscription("Evento/");

        listEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent info = new Intent(view.getContext(), InformacionEvento.class);
                if (perfil.getEventosCreados().contains(misEventos.get(position)))
                    info.putExtra("pantalla", 0);
                else
                    info.putExtra("pantalla", 2);
                startActivity(info);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        actualizar();
    }

    private Evento obtenerEvento(DataSnapshot singleSnapShot) {
        HashMap<String, Object> datos = (HashMap<String, Object>) singleSnapShot.getValue();
        DataSnapshot dsFecha = singleSnapShot.child("fecha/");
        String deporteId = datos.get("deporte").toString();
        Evento evento = new Evento();
        evento.setId(singleSnapShot.getKey());
        evento.setNombre(datos.get("nombre").toString());
        evento.setPrecio(datos.get("precio").toString());
        evento.setFecha(dsFecha.getValue(Date.class));
        evento.setDeporte(obtenerDeporte(deporteId));
        return evento;
    }

    private Deporte obtenerDeporte(final String deporteId) {
        final Deporte deporte = new Deporte();
        Almacenamiento almacenamiento = new Almacenamiento() {
            @Override
            public void leerDatos(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                if (deporteId.equals(singleSnapShot.getKey())) {
                    deporte.setNombre(datos.get("nombre").toString());
                }
                actualizarEventosUsuario();
            }
        };
        almacenamiento.loadOnce("Deporte/");
        return deporte;
    }

    private void actualizar() {
        actualizarEventosUsuario();
    }

    private void actualizarEventosUsuario() {
        misEventos = perfil.getEventos();
        eventosAdapter = new EventosAdapter(this, misEventos, true, false);
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
        misEventos = perfil.getEventos();
        int id = item.getItemId();
        if (id == R.id.menu_it_date) {
            Collections.sort(misEventos, new Evento.FechaSorter());
        } else if (id == R.id.menu_it_name) {
            Collections.sort(misEventos, new Evento.NombreSorter());
        } else if (id == R.id.menu_it_pay) {
            Collections.sort(misEventos, new Evento.PagoSorter());
        } else if (id == R.id.menu_it_sport) {
            Collections.sort(misEventos, new Evento.DeporteSorter());
        }
        eventosAdapter = new EventosAdapter(this, misEventos, true, false);
        listEventos.setAdapter(eventosAdapter);
        return super.onOptionsItemSelected(item);
    }
}

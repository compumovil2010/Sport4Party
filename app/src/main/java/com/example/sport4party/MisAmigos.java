package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MisAmigos extends AppCompatActivity {

    ListView misAmigos;
    JugadorAdapter adapter;
    ArrayList<Jugador> amigos;
    private FirebaseAuth mAuth;
    private String rutaJugadores = "Jugador/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_amigos);

        misAmigos = findViewById(R.id.listaamigos);
        mAuth = FirebaseAuth.getInstance();

        Jugador origin = new Jugador();
        obtenerJugadorActual(origin);
    }

    private void obtenerJugadorActual(final Jugador actual){
        final int[] conta = {0};
        Almacenamiento almJugador = new Almacenamiento(){
            @Override
            public void leerDatosSubscrito(final HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                super.leerDatosSubscrito(datos, singleSnapShot);
                if(datos != null){
                    actual.setNombreUsuario(datos.get("nombreUsuario").toString());
                    actual.setCorreo(datos.get("correo").toString());
                    actual.setId(singleSnapShot.getKey());

                    if(datos.get("amigos") != null){
                        final HashMap<String,String> amigos= (HashMap<String,String>)datos.get("amigos");
                        final ArrayList<Jugador> listAmigos = new ArrayList<>();
                        for (String j: amigos.keySet()) {
                            Almacenamiento almacenamientoAmigos = new Almacenamiento(){
                                @Override
                                public void leerDatosSubscrito(final HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                                    super.leerDatosSubscrito(datos, singleSnapShot);
                                    if(datos != null){
                                        Jugador amigo = new Jugador();
                                        amigo.setNombreUsuario(datos.get("nombreUsuario").toString());
                                        amigo.setCorreo(datos.get("correo").toString());
                                        amigo.setId(singleSnapShot.getKey());
                                        amigo.setSexo(datos.get("sexo").toString());
                                        listAmigos.add(amigo);
                                    }
                                    if(conta[0] == amigos.size()){
                                        pintar(listAmigos, actual);
                                    }

                                }
                            };
                            almacenamientoAmigos.obtenerPorID(rutaJugadores, j);
                            conta[0] += 1;
                        }
                    }
                }
            }
        };
        almJugador.obtenerPorID(rutaJugadores, mAuth.getUid());
    }

    private void pintar(final ArrayList<Jugador>amigos, Jugador base){
        base.setAmigos(amigos);
        adapter = new JugadorAdapter(this, amigos, false, false, base, null);
        misAmigos.setAdapter(adapter);

        misAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                Jugador miPerfil = amigos.get(position);
                infoPerfil.putExtra("jugador", miPerfil.getId());
                infoPerfil.putExtra("tipo", "1");
                startActivity(infoPerfil);
            }
        });
    }
}


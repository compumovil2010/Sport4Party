package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewDebug;
import android.widget.ListView;

import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Modelo.Mensaje;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChatEvento extends AppCompatActivity {
    private String eventoId;
    private List<Mensaje> mensajes;
    private List<String> idMensajes;
    private ListView listMensajes;
    private MensajeAdapter mensajeAdapter;
    //Autenticación
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_evento);

        iniciarVista();
        Intent intent = getIntent();
        eventoId = intent.getStringExtra("eventoId");
        mAuth = FirebaseAuth.getInstance();

        mensajes = new ArrayList<>();
        idMensajes = new ArrayList<>();

        Almacenamiento almacenamiento = new Almacenamiento() {
            @Override
            public void leerDatosSubscrito(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                if(datos.containsKey("salaDeChat")){
                    DataSnapshot dtMensajes = singleSnapShot.child("salaDeChat/");
                    for(DataSnapshot i : dtMensajes.getChildren()) {
                        if(!idMensajes.contains(i.getKey())){
                            idMensajes.add(i.getKey());
                        }
                    }
                }
            }
        };
        almacenamiento.obtenerPorID("Evento/", eventoId);

        Almacenamiento almacenamiento2 = new Almacenamiento() {
            @Override
            public void leerDatosSubscrito(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                String mensajeId = singleSnapShot.getKey();
                if (idMensajes.contains(mensajeId)) {
                    mensajes.add(obtenerMensaje(singleSnapShot));
                }
                actualizarMensajes();
            }
        };
        almacenamiento2.loadSubscription("Mensaje/");
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizar();
    }

    private Mensaje obtenerMensaje(DataSnapshot singleSnapShot) {
        HashMap<String, Object> datos = (HashMap<String, Object>) singleSnapShot.getValue();
        Jugador jugador = new Jugador();
        jugador.setNombreUsuario("LOL");
        Mensaje mensaje = new Mensaje();
        mensaje.setRemitente(jugador);
        mensaje.setContenido(datos.get("contenido").toString());
        mensaje.setFecha(singleSnapShot.child("fecha/").getValue(Date.class));
        return mensaje;
    }

    private void actualizar() { actualizarMensajes(); };

    private void actualizarMensajes() {
        if(mensajes != null){
            mensajeAdapter = new MensajeAdapter(this, mensajes);
            listMensajes.setAdapter(mensajeAdapter);
        }
    }

    private void iniciarVista() {
        listMensajes = (ListView) findViewById(R.id.listViewChat);
    }
}

package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class InvitarAmigos extends AppCompatActivity {

    ListView inivitarAmigos;
    ArrayList<Jugador> friends;
    ArrayList<Jugador> participantes;
    JugadorAdapter adapter;
    Jugador origin;
    Almacenamiento almacenamiento;
    final String rutaJugadores = "Jugador/";
    final String rutaEventos = "Evento/";
    String rutaEvento;
    int cantGenteInscrita = 0;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitar_amigos);


        inivitarAmigos = findViewById(R.id.listaamigos);

        mAuth = FirebaseAuth.getInstance();

        rutaEvento = getIntent().getStringExtra("idEvento");
    }

    @Override
    protected void onResume() {
        super.onResume();
        origin = new Jugador();
        origin.setAmigos(new ArrayList<Jugador>());
        participantes = new ArrayList<>();
        friends = new ArrayList<>();
        obtenerJugadorActual(origin);
    }

    private void buscarJugadoresBD(final String idLocalizacion, final Jugador actual){

        almacenamiento = new Almacenamiento(){
            @Override
            public void leerDatosSubscrito(final HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                super.leerDatosSubscrito(datos, singleSnapShot);
                System.out.println("key: "+singleSnapShot.getKey());
                Jugador jugadorEnEvento = new Jugador();
                if(datos.get("eventos") != null){
                    HashMap<String,String> eventos= (HashMap<String,String>)datos.get("eventos");
                    for (String i: eventos.keySet() ) {
                        Log.i("Evento actual ",eventos.get(i).toString());
                        if(eventos.get(i).toString().equals(idLocalizacion)){
                            System.out.println("estoy en el evento: "+idLocalizacion);
                            jugadorEnEvento.setNombreUsuario(datos.get("nombreUsuario").toString());
                            jugadorEnEvento.setCorreo(datos.get("correo").toString());
                            jugadorEnEvento.setId(singleSnapShot.getKey());
                            Evento e = new Evento();
                            e.setId(rutaEvento);
                            jugadorEnEvento.setEventos(new ArrayList<Evento>());
                            jugadorEnEvento.setEventosCreados(new ArrayList<Evento>());
                            jugadorEnEvento.getEventos().add(e);
                            System.out.println("voy a anadir a: "+jugadorEnEvento.getNombreUsuario());
                        }
                    }

                }if(datos.get("eventosCreados") != null){
                    HashMap<String,String> eventos= (HashMap<String,String>)datos.get("eventosCreados");
                    for (String i: eventos.keySet() ) {
                        if(eventos.get(i).equals(idLocalizacion)){
                            jugadorEnEvento.setNombreUsuario(datos.get("nombreUsuario").toString());
                            jugadorEnEvento.setCorreo(datos.get("correo").toString());
                            jugadorEnEvento.setId(singleSnapShot.getKey());
                            jugadorEnEvento.setId(singleSnapShot.getKey());
                            Evento e = new Evento();
                            e.setId(rutaEvento);
                            jugadorEnEvento.setEventos(new ArrayList<Evento>());
                            jugadorEnEvento.setEventosCreados(new ArrayList<Evento>());
                            jugadorEnEvento.getEventosCreados().add(e);

                            System.out.println("voy a anadir a: "+jugadorEnEvento.getNombreUsuario());
                        }

                    }
                }
                Log.i("Respuesta: ","Se encontro a : "+jugadorEnEvento.getNombreUsuario()+" con id: "+jugadorEnEvento.getId());

                Log.i("Respuesta: ","Cantidad de amigos del jugador actual: "+actual.getAmigos().size());
                //Log.i("Nombre: ","SE LLAMA: "+jugadores.get(jugadores.size()-1).getNombreUsuario().toString());

                if(jugadorEnEvento.getNombreUsuario() != null)
                    getCantParticipantes(jugadorEnEvento, actual);

            }
        };
        almacenamiento.loadSubscription(rutaJugadores);

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
                        System.out.println("amigos size: "+amigos.size());
                        for (String j: amigos.keySet()) {
                            Almacenamiento almacenamientoAmigos = new Almacenamiento(){
                                @Override
                                public void leerDatosSubscrito(final HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                                    super.leerDatosSubscrito(datos, singleSnapShot);
                                    if(datos != null){
                                        Jugador amigo = new Jugador();
                                        boolean esta = false;
                                        amigo.setNombreUsuario(datos.get("nombreUsuario").toString());
                                        amigo.setCorreo(datos.get("correo").toString());
                                        amigo.setId(singleSnapShot.getKey());
                                        amigo.setSexo(datos.get("sexo").toString());
                                        amigo.setEventos(new ArrayList<Evento>());
                                        amigo.setEventosCreados(new ArrayList<Evento>());
                                        for (Jugador j: actual.getAmigos()) {
                                            if(j.getId().equals(amigo.getId())){
                                                esta = true;
                                            }
                                        }
                                        if(!esta){
                                            actual.getAmigos().add(amigo);
                                        }
                                    }
                                    if(conta[0] == amigos.size()){
                                        System.out.println("si tengo amigos pero no los seteo");
                                        buscarJugadoresBD(rutaEvento, actual);
                                    }

                                }
                            };
                            almacenamientoAmigos.obtenerPorID(rutaJugadores, j);
                            conta[0] += 1;
                        }
                    }else{
                        actual.setAmigos(new ArrayList<Jugador>());
                        System.out.println("No tengo amigos"+actual.getAmigos().size());
                        buscarJugadoresBD(rutaEvento,actual);
                    }
                }
            }
        };
        almJugador.obtenerPorID(rutaJugadores, mAuth.getUid());
    }

    private void getCantParticipantes(final Jugador playerFound, final Jugador base){

        final Evento actual = new Evento();
        Almacenamiento almaEvento = new Almacenamiento(){
            @Override
            public void leerDatosSubscrito(final HashMap<String, Object> datos, DataSnapshot singleSnapShot){
                super.leerDatosSubscrito(datos, singleSnapShot);
                if(datos != null){
                    actual.setId(singleSnapShot.getKey());
                    actual.setCupos(Integer.parseInt(datos.get("cupos").toString()));

                    boolean ad = true;
                    for (Jugador j: participantes) {
                        System.out.println("de j: "+j.getId());
                        System.out.println("del que llego id: "+playerFound.getId());
                        if(j.getId().equals(playerFound.getId())){
                            ad = false;
                            System.out.println(j.getNombreUsuario()+" Ya estaaaaa");
                        }
                    }
                    origin = base;
                    friends = (ArrayList<Jugador>) origin.getAmigos();
                    System.out.println(origin.getId()+" mmmm: "+origin.getNombreUsuario());
                    if(ad){
                        participantes.add(playerFound);
                        cantGenteInscrita = participantes.size();
                        System.out.println("Gente inscrita: "+cantGenteInscrita+" en "+rutaEvento);
                    }
                    for (Jugador a: participantes) {
                        for (Jugador b: origin.getAmigos()) {
                            if(a.getId().equals(b.getId())){
                                b.getEventos().add(actual);
                            }
                        }
                    }

                    adapter = new JugadorAdapter(getApplicationContext(), friends, false, true, origin,actual, cantGenteInscrita);
                    inivitarAmigos.setAdapter(adapter);
                    inivitarAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                            Jugador miPerfil = friends.get(position);
                            infoPerfil.putExtra("jugador", miPerfil.getId());
                            infoPerfil.putExtra("tipo", "1");
                            startActivity(infoPerfil);
                        }
                    });





                }
            }
        };
        almaEvento.obtenerPorID(rutaEventos,rutaEvento);

    }
}

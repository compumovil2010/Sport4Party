package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class VerParticipantes extends AppCompatActivity {

    Button invitarAmigos;
    Button localizar;
    ListView listaAsistentes;
    JugadorAdapter adapter;
    ArrayList<Jugador> participantes;
    Almacenamiento almacenamiento;
    final String rutaJugadores = "Jugador/";
    String rutaEvento;
    private FirebaseAuth mAuth;
    Jugador origin;
    int cantGenteInscrita = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_participantes);

        invitarAmigos = findViewById(R.id.botonInvitarAmigos);
        localizar = findViewById(R.id.botonLocalizar);
        listaAsistentes = findViewById(R.id.listaamigos);

        rutaEvento = getIntent().getStringExtra("idEvento");

        invitarAmigos.setClickable(false);
        invitarAmigos.setVisibility(View.INVISIBLE);
        localizar.setClickable(false);
        localizar.setVisibility(View.INVISIBLE);


        localizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),RutaAmigos.class);
                intent.putExtra("id",rutaEvento);
                startActivity(intent);
            }
        });
        invitarAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInvitarAmigos = new Intent(v.getContext(), InvitarAmigos.class);

                //Se debería incluir la capacidad máxima del evento, con el fin de que al agregar no se pase del límite establecido
                //Amigos debería ser la lista de Jugador que tiene el usuario actual
                intentInvitarAmigos.putExtra("idEvento", rutaEvento);
                startActivity(intentInvitarAmigos);
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();
        origin = new Jugador();
        origin.setAmigos(new ArrayList<Jugador>());
        participantes = new ArrayList<>();
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

                      }

                  }
              }
              Log.i("Respuesta: ","Se encontro a : "+jugadorEnEvento.getNombreUsuario()+" con id: "+jugadorEnEvento.getId());

              Log.i("Respuesta: ","Cantidad de amigos del jugador actual: "+actual.getAmigos().size());
              //Log.i("Nombre: ","SE LLAMA: "+jugadores.get(jugadores.size()-1).getNombreUsuario().toString());
              if(jugadorEnEvento.getNombreUsuario() != null)
                pintar(jugadorEnEvento, actual);


              //jugadores.clear();

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
                                        System.out.println("Voy a ver si esta_-----"+amigo.getNombreUsuario());
                                        for (Jugador j: actual.getAmigos()) {
                                            if(j.getId().equals(amigo.getId())){
                                                System.out.println("si estaba");
                                                esta = true;
                                            }
                                        }
                                        if(!esta){
                                            System.out.println("no estaba y por tanto sumo");
                                            actual.getAmigos().add(amigo);
                                        }
                                    }
                                    if(conta[0] == amigos.size()){
                                        System.out.println("si tengo amigos pero no los seteo");
                                        buscarJugadoresBD(rutaEvento, actual);
                                        //falta poner a actual en el metodo para ponerle los amigos...
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

    void pintar(Jugador playerFound, Jugador base){
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
        //Almacenamiento almacenamiento = new Almacenamiento();
        //almacenamiento.push("SQTAjpRSpQQKtfT76J5O5WSfZ3P2", "Jugador/" + origin.getId() + "/amigos/", "SQTAjpRSpQQKtfT76J5O5WSfZ3P2");
        //almacenamiento.push(origin.getId(), "Jugador/" + "SQTAjpRSpQQKtfT76J5O5WSfZ3P2" + "/amigos/", origin.getId());

        for (Jugador j: origin.getAmigos()          ) {
            System.out.println("EN MIS AMIGOS ESTAN: "+j.getNombreUsuario());
        }

        System.out.println(base.getId()+" mmmm: "+base.getNombreUsuario());
        if(ad){
            participantes.add(playerFound);
            cantGenteInscrita = participantes.size();
            System.out.println("Gente inscrita: "+cantGenteInscrita);
            System.out.println("vamos a cambiar");
            adapter = new JugadorAdapter(getApplicationContext(), participantes, true, false, origin, null,0);
            listaAsistentes.setAdapter(adapter);
            boolean estoy = false;
            for (Jugador j: participantes) {
                if(j.getId().equals(base.getId())){
                    estoy = true;
                }
            }
            if(!estoy){
                invitarAmigos.setClickable(false);
                invitarAmigos.setVisibility(View.INVISIBLE);
                localizar.setClickable(false);
                localizar.setVisibility(View.INVISIBLE);
            }else{
                invitarAmigos.setClickable(true);
                invitarAmigos.setVisibility(View.VISIBLE);
                localizar.setClickable(true);
                localizar.setVisibility(View.VISIBLE);
            }
            
        }

        listaAsistentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean entre = false;
                Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                Jugador perfil = participantes.get(position);
                infoPerfil.putExtra("jugador", perfil.getId());
                for (Jugador j: origin.getAmigos()) {
                    System.out.println("estoy buscando a :"+perfil.getNombreUsuario());
                    System.out.println("contra: "+j.getNombreUsuario());
                    if(perfil.getId().equals(j.getId())){
                        System.out.println("encontre a "+perfil.getNombreUsuario());
                        infoPerfil.putExtra("tipo", "1");
                        startActivity(infoPerfil);
                        entre = true;
                    }
                }
                if(!entre && perfil.getId().equals(origin.getId())){
                    System.out.println("Soy yo mismo");
                    infoPerfil.putExtra("tipo", "0");
                    startActivity(infoPerfil);

                }
                else if(!entre){
                    System.out.println("No lo encontre en mis amigos"+perfil.getNombreUsuario());
                    infoPerfil.putExtra("tipo", "2");
                    startActivity(infoPerfil);
                }
            }
        });
    }
}



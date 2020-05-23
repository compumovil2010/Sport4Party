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
    //final String pathEventos = "Eventos/";
    String rutaEvento;
    private FirebaseAuth mAuth;
    Jugador origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_participantes);

        invitarAmigos = findViewById(R.id.botonInvitarAmigos);
        localizar = findViewById(R.id.botonLocalizar);
        listaAsistentes = findViewById(R.id.listaamigos);

        //Extraigo el Bundle con la información de los participantes del envento
        final Bundle info = getIntent().getBundleExtra("listaParticipantes");
        rutaEvento = getIntent().getStringExtra("idEvento");
        Log.i("Ruta evento", rutaEvento);
        //Asigno la lista de participantes al ArrayList que va a manejar el adaptador
        participantes = (ArrayList<Jugador>) info.getSerializable("participantes");

        //La idea es que este sea el perfil que se maneja en la aplicacion
        origin = new Jugador("puf","asb", "asb", "Mael", "masculino");
        origin.addAmigos(participantes.get(0));
        origin.addAmigos(participantes.get(3));
        origin.addAmigos(participantes.get(6));
        adapter = new JugadorAdapter(getApplicationContext(), participantes,true, false, origin, null);
        listaAsistentes.setAdapter(adapter);

        invitarAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInvitarAmigos = new Intent(v.getContext(), InvitarAmigos.class);
                Bundle extaInfo = new Bundle();
                //Se debería incluir la capacidad máxima del evento, con el fin de que al agregar no se pase del límite establecido
                //Amigos debería ser la lista de Jugador que tiene el usuario actual
                extaInfo.putSerializable("amigos", (Serializable) participantes);
                extaInfo.putSerializable("perfilActual",(Serializable) origin);
                extaInfo.putString("idEvento", rutaEvento);
                intentInvitarAmigos.putExtra("listaDeAmigos",extaInfo);
                startActivity(intentInvitarAmigos);
            }
        });

        listaAsistentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                Jugador perfil = participantes.get(position);
                infoPerfil.putExtra("jugador", perfil);
                if(origin.getAmigos().contains(perfil))
                    infoPerfil.putExtra("tipo", "1");
                else
                    infoPerfil.putExtra("tipo", "2");
                startActivity(infoPerfil);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        //obtenerJugadorActual(origin);
        ArrayList<Jugador> listaParticipantes = new ArrayList<>();
        //Saca a cada jugador que este registrado en el evento, con sus amigos
        buscarJugadoresBD(listaParticipantes, rutaEvento);

    }



    //Aqui toca poner los jugadores de una vez en el list view
    private void buscarJugadoresBD(final ArrayList<Jugador> jugadores, final String idLocalizacion){
        final Jugador jugadorEnEvento = new Jugador();
        almacenamiento = new Almacenamiento(){
          @Override
          public void leerDatosSubscrito(final HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
              super.leerDatosSubscrito(datos, singleSnapShot);
              if(datos.get("eventos") != null){
                  HashMap<String,String> eventos= (HashMap<String,String>)datos.get("eventos");
                  for (String i: eventos.keySet() ) {
                      Log.i("Evento actual ",eventos.get(i).toString());
                      if(eventos.get(i).toString().equals(idLocalizacion)){
                          jugadorEnEvento.setNombreUsuario(datos.get("nombreUsuario").toString());
                          jugadorEnEvento.setCorreo(datos.get("correo").toString());
                          jugadorEnEvento.setId(singleSnapShot.getKey());
                          jugadores.add(jugadorEnEvento);
                      }
                  }

              }else if(datos.get("eventosCreados") != null){
                  HashMap<String,String> eventos= (HashMap<String,String>)datos.get("eventosCreados");
                  for (String i: eventos.keySet() ) {
                      if(eventos.get(i).equals(idLocalizacion)){
                          jugadorEnEvento.setNombreUsuario(datos.get("nombreUsuario").toString());
                          jugadorEnEvento.setCorreo(datos.get("Correo").toString());
                          jugadorEnEvento.setId(singleSnapShot.getKey());
                          jugadores.add(jugadorEnEvento);
                      }

                  }
              }
              Log.i("Respuesta: ","Cantidad de jugadores en el evento: "+jugadores.size());
              //Log.i("Nombre: ","SE LLAMA: "+jugadores.get(jugadores.size()-1).getNombreUsuario().toString());
              /*
                adapter = new JugadorAdapter(getApplicationContext(), jugadores, true, false, origin, null);
                listaAsistentes.setAdapter(adapter);
              */
          }
        };
        almacenamiento.loadSubscription(rutaJugadores);

    }

    private void obtenerJugadorActual(final Jugador actual){
        Almacenamiento almJugador = new Almacenamiento(){
            @Override
            public void leerDatosSubscrito(final HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                super.leerDatosSubscrito(datos, singleSnapShot);
                if(datos != null){
                    actual.setNombreUsuario(datos.get("nombreUsuario").toString());
                    actual.setCorreo(datos.get("correo").toString());
                    actual.setId(singleSnapShot.getKey());

                    if(datos.get("amigos") != null){
                        HashMap<String,String> amigos= (HashMap<String,String>)datos.get("amigos");
                        final ArrayList<Jugador> listAmigos = new ArrayList<>();
                        for (String j: amigos.keySet()) {
                            Almacenamiento almacenamientoAmigos = new Almacenamiento(){
                                @Override
                                public void leerDatosSubscrito(final HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                                    super.leerDatosSubscrito(datos, singleSnapShot);
                                    Jugador amigo = new Jugador();
                                    amigo.setNombreUsuario(datos.get("nombreUsuario").toString());
                                    amigo.setCorreo(datos.get("Correo").toString());
                                    amigo.setId(singleSnapShot.getKey());
                                    amigo.setSexo(datos.get("sexo").toString());
                                    listAmigos.add(amigo);
                                }
                            };
                            almacenamientoAmigos.loadSubscription(rutaJugadores+amigos.get(j));
                        }
                        actual.setAmigos(listAmigos);
                    }

                }
                /*ArrayList<Jugador> listaParticipantes = new ArrayList<>();
                //Saca a cada jugador que este registrado en el evento, con sus amigos
                buscarJugadoresBD(listaParticipantes, rutaEvento);*/
            }
        };
        almJugador.loadSubscription(rutaJugadores+mAuth.getUid());
    }
}

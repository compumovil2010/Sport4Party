package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;
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
    JugadorAdapter adapter;
    Jugador origin;
    final String rutaJugadores = "Jugador/";
    final String rutaEventos = "Evento/";
    String idEvento;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitar_amigos);


        inivitarAmigos = findViewById(R.id.listaamigos);

        mAuth = FirebaseAuth.getInstance();

        //obtenerJugadorActual(origin, rutaJugadores+mAuth.getUid());
        //Con esto de arriba solo se queda lo de idEvento y el listener que pues ya depende de lo que pida el perfil

        //Extraigo el Bundle con la información de los amigos del usuario actual
        Bundle bundleInfo = getIntent().getBundleExtra("listaDeAmigos");

        //Asigno la lista de amigos al ArrayList que va a manejar el adaptador
        friends = (ArrayList<Jugador>) bundleInfo.getSerializable("amigos");
        origin = (Jugador) bundleInfo.getSerializable("perfilActual");
        idEvento = bundleInfo.getString("idEvento");

        adapter = new JugadorAdapter(getApplicationContext(), friends, false, true, origin,null);
        inivitarAmigos.setAdapter(adapter);

        //Funcionalidad que debe "evitar" que el usuario añada a amigos que ya estén inscritos en el evento
        //Utilizar la información del límite de personas para el evento, con tal de no añadir de más

        inivitarAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent infoPerfil = new Intent(view.getContext(), Perfil.class);
                Jugador miPerfil = friends.get(position);
                infoPerfil.putExtra("jugador", miPerfil);
                infoPerfil.putExtra("tipo", "1");
                startActivity(infoPerfil);
            }
        });

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
                //Evento aVer = new Evento();
                //obtenerEventoSobre(actual, aVer);
            }
        };
        almJugador.loadSubscription(rutaJugadores+mAuth.getUid());
    }

    private void obtenerEventoSobre(final Jugador actual, final Evento actualE){
        Almacenamiento almEvento = new Almacenamiento(){
            @Override
            public void leerDatosSubscrito(final HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                super.leerDatosSubscrito(datos, singleSnapShot);
                if(datos != null){
                    //MIRAR, EN DADO CASO QUE DIGAN QUE LOS CUPOS ESTAN EN EL DEPORTE TOCA HACER LA BUSQUEDA DEL DEPORTE
                    actualE.setCupos((long) datos.get("cupos"));
                    actualE.setId(singleSnapShot.getKey());
                }
                //adapter = new JugadorAdapter(getApplicationContext(), (ArrayList<Jugador>) actual.getAmigos(), false, true, origin, actualE);
                //inivitarAmigos.setAdapter(adapter);
            }
        };
        almEvento.loadSubscription(rutaEventos+idEvento);
    }
}

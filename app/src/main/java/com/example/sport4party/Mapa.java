package com.example.sport4party;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.example.sport4party.Modelo.Deporte;
import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Modelo.Ubicacion;

import android.util.EventLog;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.sport4party.Utils.LocationFinder;
import com.example.sport4party.Utils.UbicationFinder;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mapa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private AppBarConfiguration mAppBarConfiguration;
    Intent opcion;
    private ArrayList<Jugador> jugadores;
    private GoogleMap mMap;
    private UbicationFinder ubicationFinder;
    private LocationFinder gCoderHandler;
    private Marker myPosition;
    private Jugador jugador;
    private List<Evento> eventos;

    Spinner hora;
    Spinner deportes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FakeInformation-------------------------------------------------------
        opcion=getIntent();
        if(mMap!=null)
        {
            mMap.clear();
        }
        Deporte deportePrueba = new Deporte(12, "futbol");

        Ubicacion ubicacion1 = new Ubicacion("Prueba 1", new Date(), new Double(4.618234), new Double(-74.069133), true);
        Ubicacion ubicacion2 = new Ubicacion("Prueba 2", new Date(), new Double(4.630430), new Double(-74.0822808), true);
        Ubicacion ubicacion3 = new Ubicacion("Prueba 3", new Date(), new Double(4.588268), new Double(-74.100860), true);
        Ubicacion ubicacion4 = new Ubicacion("Prueba 4", new Date(), new Double(4.638389), new Double(-74.141524), false);

        Evento evento1 = new Evento(111, "evento 1", new Date(), "Bajo", "Evento 1", "0", false, false, deportePrueba, ubicacion1);
        Evento evento2 = new Evento(111, "evento 2", new Date(), "Bajo", "Evento 2", "0", false, false, deportePrueba, ubicacion2);
        Evento evento3 = new Evento(111, "evento 3", new Date(), "Bajo", "Evento 3", "0", false, false, deportePrueba, ubicacion2);
        Evento evento4 = new Evento(111, "evento 4", new Date(), "Bajo", "Evento 4", "0", false, true, deportePrueba, ubicacion3);

        eventos = new ArrayList<>();
        this.eventos.add(evento1);
        this.eventos.add(evento2);
        this.eventos.add(evento3);
        this.eventos.add(evento4);


        jugador = new Jugador("123456", "yolo@g.com", "yolo", "Masculino");
        jugador.addEventos(evento1);
        jugador.addEventos(evento2);
        jugador.addEventos(evento3);
        jugador.addEventos(evento4);

        //----------------------------------------------------------------------

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        hora = findViewById(R.id.spinnerHour);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.hours, R.layout.text_color_spinner_deportes);
        adapter.setDropDownViewResource(R.layout.deportes_dropdown);
        hora.setAdapter(adapter);

        deportes = findViewById(R.id.spinnerSport);
        ArrayAdapter adp = ArrayAdapter.createFromResource(this, R.array.sports, R.layout.text_color_spinner_deportes);
        adp.setDropDownViewResource(R.layout.deportes_dropdown);
        deportes.setAdapter(adp);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //MAPA
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gCoderHandler = new LocationFinder(Mapa.this);
        myPosition = null;
        CrearEventosNuevo();
    }
    private void CrearEventosNuevo()
    {
        opcion = getIntent();
        if(opcion!=null)
        {
            int pantalla=opcion.getIntExtra("pantalla",-1);
            if(pantalla==1)
            {
                LinearLayout cosasInnecesarias=findViewById(R.id.cosasInecesarias);
                cosasInnecesarias.setVisibility(View.INVISIBLE);
            }
        }
        return;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.nav_evento){
            Intent change = new Intent(this, Mapa.class);
            startActivity(change);
        }
        else if (id == R.id.nav_mis_amigos){
            jugadores = new ArrayList<>();
            jugadores.add(new Jugador("asd","asd", "Juan Francisco Hamon", "Masculino"));
            jugadores.add(new Jugador("asd","asd", "Diego Barajas", "Masculino"));
            jugadores.add(new Jugador("asd","asd", "Brandonn Cruz", "Masculino"));
            jugadores.add(new Jugador("asd","asd", "Santiago Chaparro", "Masculino"));
            jugadores.add(new Jugador("asd","asd", "Pedro Fernandez", "Masculino"));
            jugadores.add(new Jugador("asd","asd", "Santiago Herrera", "Masculino"));
            jugadores.add(new Jugador("asd","asd", "Carlos Orduz", "Masculino"));
            jugadores.add(new Jugador("asd","asd", "Diego Ignacio Martinez", "Masculino"));
            Intent change = new Intent(this, MisAmigos.class);
            Bundle info = new Bundle();
            info.putSerializable("amigos", jugadores);
            change.putExtra("listaAmigos",info);
            startActivity(change);
        }else if(id == R.id.nav_mis_eventos){
            Intent change = new Intent(this, misEventos.class);
            Jugador miPerfil = new Jugador("asd", "asd", "Mael", "Masculino");

            Deporte futbol = new Deporte(10, "Futbol");
            Deporte patinaje = new Deporte(10,"Patinaje");
            Ubicacion ubicacion = new Ubicacion("ubicacion de prueba", new Date(), new Double(0), new Double(0), true);
            Evento evento1 = new Evento(10, "atletismo", new Date(),"bueno", "Evento 1", "2000 pesos", true, true, futbol, ubicacion);
            miPerfil.addEventos(evento1);
            Evento evento2 = new Evento(20, "atletismo", new Date(),"bueno", "Evento 1", "2000 pesos", true, true, patinaje, ubicacion);
            miPerfil.addEventos(evento2);
            change.putExtra("jugador",miPerfil);
            startActivity(change);

        }else if(id == R.id.nav_mi_perfil){
            Intent change = new Intent(this, Perfil.class);
            Jugador miPerfil = new Jugador("asd", "asd", "Mael", "Masculino");

            Deporte futbol = new Deporte(10, "Futbol");
            Deporte patinaje = new Deporte(10,"Patinaje");
            Ubicacion ubicacion = new Ubicacion("ubicacion de prueba", new Date(), new Double(0),new Double(0), true);
            Evento evento = new Evento(1, "atletismo", new Date(),"bueno", "Evento 1", "2000 pesos", true, true, futbol, ubicacion);
            miPerfil.addEventos(evento);
            Evento evento2 = new Evento(2, "atletismo2", new Date(),"bueno", "Evento 2", "2000 pesos", false, false, patinaje, ubicacion);
            miPerfil.addEventos(evento2);
            change.putExtra("jugador", miPerfil);
            change.putExtra("tipo", "0");
            startActivity(change);
        }else if(id == R.id.nav_cerrar_sesion){
            Intent change = new Intent(this, InicioDeSesión.class);
            Toast.makeText(this,"Sesion cerrada",Toast.LENGTH_LONG).show();
            startActivity(change);
        }else if(id == R.id.nav_rutas){
            Intent change = new Intent(this, RutaEvento.class);
            startActivity(change);
        }
        //Agregar todos los Intents

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void toInformacionEvento(Marker marker, int pantalla){
            Intent intent = new Intent(this, InformacionEvento.class);
            intent.putExtra("pantalla",pantalla);
            mMap.clear();
            startActivity(intent);
            loadMarkers(eventos);
    }

    //MAP HANDLER
    public void addMarkerUbication(LatLng position){
        mMap.addMarker(new MarkerOptions().position(position).title(gCoderHandler.searchFromLocation(position, 1).getAddressLine(0)));
    }

    public void addMyPosition(LatLng position){
        if(myPosition == null){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
        else{
            myPosition.remove();
        }
        myPosition = mMap.addMarker(new MarkerOptions().position(position).title(gCoderHandler.searchFromLocation(position, 1).getAddressLine(0)));
    }

    public void loadMarkers(List<Evento> eventos){
        for (Evento evento: eventos) {
            if(!evento.isPrivado() && evento.getUbicacion().isValida()){
                LatLng position = new LatLng(evento.getUbicacion().getLatitud(), evento.getUbicacion().getLongitud());
                addMarkerUbication(position);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                int tipo;
                if(opcion.getIntExtra("pantalla",-1)==-1)
                {
                    tipo=1;
                }
                else
                {
                    tipo=3;
                }
                if(myPosition!=null) {
                    if (!marker.getId().equals(myPosition.getId())) {
                        toInformacionEvento(marker, tipo);
                    }
                }
                else
                {
                    toInformacionEvento(marker,tipo);
                }
                return false;
            }

        });
        loadMarkers(this.eventos);

        ubicationFinder = new UbicationFinder(this){
            @Override
            public void onLocation(Location location) {
                if (location != null && mMap != null) {
                    Mapa.this.addMyPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        //sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (ubicationFinder != null)
            ubicationFinder.startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mMap!=null)
        {
            mMap.clear();
        }
        //sensorManager.unregisterListener(lightSensorListener);
        if (ubicationFinder != null)
            ubicationFinder.stopLocationUpdates();
            myPosition = null;
    }
}

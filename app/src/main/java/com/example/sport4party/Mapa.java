package com.example.sport4party;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;

import com.example.sport4party.Modelo.Deporte;
import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Modelo.Mensaje;
import com.example.sport4party.Modelo.Opinion;
import com.example.sport4party.Modelo.Ubicacion;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.sport4party.Utils.Almacenamiento;
import com.example.sport4party.Utils.LocationFinder;
import com.example.sport4party.Utils.ServiceAmigos;
import com.example.sport4party.Utils.UbicationFinder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    NavigationView navigationView;
    private FirebaseAuth mAuth;
    private HashMap<String, Marker> eventos;
    private SearchView buscarEnMapa;
    private SensorManager sensorManager;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    Sensor lightSensor;
    SensorEventListener lightSensorListener;

    private void quemar()
    {

        jugador = new Jugador(FirebaseAuth.getInstance().getUid(),"123456", "yolo@g.com", "yolo", "Masculino");

        Deporte deportePrueba1 = new Deporte(12, "futbol");
        Deporte deportePrueba2 = new Deporte(12, "baloncesto");
        Deporte deportePrueba3 = new Deporte(12, "volleyball");
        Deporte deportePrueba4 = new Deporte(12, "atletismo");
        deportePrueba1.setId("1");
        //deportePrueba1.pushFireBaseBD();
        deportePrueba2.setId("2");
        //deportePrueba2.pushFireBaseBD();
        deportePrueba3.setId("3");
        //deportePrueba3.pushFireBaseBD();
        deportePrueba4.setId("4");
        //deportePrueba4.pushFireBaseBD();


        List<Deporte> deportes=new ArrayList<Deporte>();
        deportes.add(deportePrueba1);
        deportes.add(deportePrueba4);
        Ubicacion ubicacion1 = new Ubicacion("Prueba 1", new Date(), new Double(4.618234), new Double(-74.069133), true);
        ubicacion1.setDeportesDisponibles(deportes);
        ubicacion1.setId("69");
        //ubicacion1.pushFireBaseBD();

        List<Deporte> deportes99=new ArrayList<Deporte>();
        Ubicacion ubicacion99 = new Ubicacion("Prueba 4", new Date(), new Double(4.572), new Double(-74.1337), true);
        ubicacion99.setDeportesDisponibles(deportes99);
        ubicacion99.setId("10");
        //ubicacion99.pushFireBaseBD();

        deportes.add(deportePrueba2);
        Ubicacion ubicacion2 = new Ubicacion("Prueba 2", new Date(), new Double(4.630430), new Double(-74.0822808), true);
        ubicacion2.setDeportesDisponibles(deportes);
        ubicacion2.setId("5");
        //ubicacion2.pushFireBaseBD();

        Ubicacion ubicacion3 = new Ubicacion("Prueba 3", new Date(), new Double(4.588268), new Double(-74.100860), true);
        ubicacion3.setDeportesDisponibles(deportes);
        ubicacion3.setId("6");
        //ubicacion3.pushFireBaseBD();

        deportes.add(deportePrueba3);
        deportes.add(deportePrueba4);
        Ubicacion ubicacion4 = new Ubicacion("Prueba 4", new Date(), new Double(4.638389), new Double(-74.141524), false);
        ubicacion4.setDeportesDisponibles(deportes);
        ubicacion4.setId("6");
        //ubicacion4.pushFireBaseBD();
        //almacenamiento.push(ubicacion1.returnMap(),"Ubicacion/");




        Evento evento1 = new Evento(0, "evento 1",4, new Date(), "Bajo", "Evento 1", "8000", true, false, deportePrueba4, ubicacion1);
        Evento evento2 = new Evento(1, "evento 2",6, new Date(), "Bajo", "Evento 2", "5000", true, false, deportePrueba3, ubicacion1);
        Evento evento3 = new Evento(2, "evento 3",10, new Date(), "Bajo", "Evento 3", "0", false, false, deportePrueba2, ubicacion3);
        Evento evento4 = new Evento(3, "evento 4",20, new Date(), "Bajo", "Evento 4", "0", false, true, deportePrueba1, ubicacion3);
        //evento1.setId("7");
        //evento2.setId("8");
        //evento3.setId("9");
        //evento4.setId("10");

        //evento1.pushFireBaseBD();
        //evento2.pushFireBaseBD();
        //evento3.pushFireBaseBD();
        //evento4.pushFireBaseBD();

        //eventos = new ArrayList<>();
        //this.eventos.add(evento1);
        //this.eventos.add(evento2);
        //this.eventos.add(evento3);
        //this.eventos.add(evento4);

        jugador.addEventos(evento1);
        jugador.addEventos(evento2);
        jugador.addEventos(evento3);
        jugador.addEventos(evento4);

        jugador.addEventoCreado(evento2);
        jugador.addEventoCreado(evento4);

        jugadores = new ArrayList<>();
        jugadores.add(new Jugador("666","asd","asd", "Juan Francisco Hamon", "Masculino"));
        jugadores.add(new Jugador("33","asd","asd", "Diego Barajas", "Masculino"));
        jugadores.add(new Jugador("88","asd","asd", "Brandonn Cruz", "Masculino"));
        jugadores.add(new Jugador("99","asd","asd", "Santiago Chaparro", "Masculino"));
        jugadores.add(new Jugador("77","asd","asd", "Pedro Fernandez", "Masculino"));
        jugadores.add(new Jugador("88","asd","asd", "Santiago Herrera", "Masculino"));
        jugadores.add(new Jugador("99","asd","asd", "Carlos Orduz", "Masculino"));
        jugadores.add(new Jugador("33","asd","asd", "Diego Ignacio Martinez", "Masculino"));
        jugador.setAmigos(jugadores);

        List<Opinion>opiniones=new ArrayList<Opinion>();
        Opinion opinion1=new Opinion( 2.0,"lal",ubicacion1,jugador);
        Opinion opinion2= new Opinion( (Double) 5.0,"lel",ubicacion1,jugador);
        opinion1.setId("15");
        opinion2.setId("14");
        //opinion1.pushFireBaseBD();
        //opinion2.pushFireBaseBD();

        opiniones.add(opinion1);
        opiniones.add(opinion2);
        jugador.setOpiniones(opiniones);

        final Mensaje mensaje1;
        Mensaje mensaje2;
        mensaje1=new  Mensaje("hola", new Date(), jugador);
        mensaje2=new  Mensaje("adios", new Date(), jugador);
        mensaje1.setId("11");
        mensaje2.setId("12");
        //mensaje1.pushFireBaseBD();
        //mensaje2.pushFireBaseBD();
        List<Mensaje> mensajes=new ArrayList<Mensaje>();
        mensajes.add(mensaje1);
        mensajes.add(mensaje2);
        jugador.setEnviados(mensajes);

        jugador.setId(FirebaseAuth.getInstance().getUid());
        //jugador.pushFireBaseBD();


        Almacenamiento buscar=new Almacenamiento()
        {
            @Override
            public void onBuscarResult(HashMap<String, Object> data, String key) {
                super.onBuscarResult(data, key);
                Log.i("mensaje: ",   (String)data.get("contenido"));
            }

        };
    }


    public void updateWhitDataBase(){
        Almacenamiento almacenamiento = new Almacenamiento();
    }

    Spinner hora;
    Spinner deportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FakeInformation-------------------------------------------------------
        opcion=getIntent();
        quemar();
        //updateWhitDataBase();
        //----------------------------------------------------------------------

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        hora = findViewById(R.id.spinnerHour);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        eventos = new HashMap<>();
        //cargarEventos();

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.hours, R.layout.text_color_spinner_deportes);
        adapter.setDropDownViewResource(R.layout.deportes_dropdown);
        hora.setAdapter(adapter);

        deportes = findViewById(R.id.spinnerSport);
        ArrayAdapter adp = ArrayAdapter.createFromResource(this, R.array.sports, R.layout.text_color_spinner_deportes);
        adp.setDropDownViewResource(R.layout.deportes_dropdown);
        deportes.setAdapter(adp);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //MAPA
        buscarEnMapa = findViewById(R.id.searchViewLocation);
        buscarEnMapa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Address ubicacion = gCoderHandler.searchFromLocationName(query, 1);
                LatLng moverCamara = new LatLng(ubicacion.getLatitude(), ubicacion.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(moverCamara));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Intent serviceAmigos = new Intent(getBaseContext(), ServiceAmigos.class);
        startService(serviceAmigos);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gCoderHandler = new LocationFinder(Mapa.this);
        myPosition = null;
        CrearEventosNuevo();
        activarSensorDeLuz();

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

    private void cargarEventos(){

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.nav_evento){
            Intent change = new Intent(this, Mapa.class);
            startActivity(change);
        }
        else if (id == R.id.nav_mis_amigos){
            Intent change = new Intent(this, MisAmigos.class);
            Bundle info = new Bundle();
            info.putSerializable("amigos", jugadores);
            change.putExtra("listaAmigos",info);
            startActivity(change);
        }else if(id == R.id.nav_mis_eventos){
            Intent change = new Intent(this, MisEventos.class);
            //Jugador miPerfil = new Jugador("asd", "asd", "Mael", "Masculino");

            //Deporte futbol = new Deporte(10, "Futbol");
            //Deporte patinaje = new Deporte(10,"Patinaje");

            //Ubicacion ubicacion = new Ubicacion("ubicacion de prueba", new Date(), new Double(0), new Double(0), true);
            //Evento evento1 = new Evento(10, "atletismo", new Date(),"bueno", "Evento 1", "2000 pesos", true, true, futbol, ubicacion);

            //miPerfil.addEventos(evento1);
            //Evento evento2 = new Evento(20, "atletismo", new Date(),"bueno", "Evento 1", "2000 pesos", true, true, patinaje, ubicacion);
            //miPerfil.addEventos(evento2);


            change.putExtra("jugador", jugador);
            startActivity(change);

        }else if(id == R.id.nav_mi_perfil){
            Intent change = new Intent(this, Perfil.class);
            change.putExtra("jugador", jugador);
            change.putExtra("tipo", "0");
            startActivity(change);
        }else if(id == R.id.nav_cerrar_sesion){
            if(ubicationFinder!=null)
                ubicationFinder.stopLocationUpdates();

            new Almacenamiento().erase("Jugador/"+FirebaseAuth.getInstance().getUid()+"/latitud/");
            new Almacenamiento().erase("Jugador/"+FirebaseAuth.getInstance().getUid()+"/longitud/");
            mAuth.signOut();
            Intent change = new Intent(this, InicioDeSesión.class);
            change.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            Toast.makeText(this,"Sesion cerrada",Toast.LENGTH_LONG).show();
            startActivity(change);
            finish();
        }
        //Agregar todos los Intents

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        actualizarUsuario(currentUser);
    }

    public void actualizarUsuario(FirebaseUser currentUser){
        if(currentUser != null) {
            //jugador.setNombreUsuario(currentUser.getDisplayName());
            jugador.setCorreo(currentUser.getEmail());
        }
    }

    public void toInformacionEvento(String id, int pantalla){
        Intent intent = new Intent(this, InformacionLugar.class);
        if(myPosition!=null)
            myPosition.remove();
        if(pantalla==1)
        {
            intent.putExtra("pantalla",pantalla);
            intent.putExtra("id", id);
            startActivity(intent);
        }
        if(pantalla==3)
        {
            intent.putExtra("pantalla",pantalla);
            intent.putExtra("id", id);
            startActivityForResult(intent,666);
        }
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
        Address address = gCoderHandler.searchFromLocation(position, 1);
        if(address != null)
            myPosition = mMap.addMarker(new MarkerOptions().position(position).title(address.getAddressLine(0)));
    }

    public void loadMarkers(){
        myRef=database.getReference("Ubicacion");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //for (Marker i : Mapa.this.eventos.values()){
                //    i.remove();
                //}

                for(DataSnapshot singleSnapShot: dataSnapshot.getChildren()) {
                    HashMap<String, Object> datos = (HashMap<String, Object>) singleSnapShot.getValue();
                    LatLng posicionAux = new LatLng((Double) datos.get("latitud"), (Double) datos.get("Longitud"));
                    Marker markerAux = Mapa.this.mMap.addMarker(new MarkerOptions().position(posicionAux).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title(singleSnapShot.getKey()));//gCoderHandler.searchFromLocation(posicionAux, 1).getAddressLine(0).toString()));//gCoderHandler.searchFromLocation(posicionEvento, 1).getAddressLine(0)));
                    //markerAux.set
                    Mapa.this.eventos.put(singleSnapShot.getKey(), markerAux);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                        toInformacionEvento(marker.getTitle(), tipo);

                    }
                }
                else
                {
                    toInformacionEvento(marker.getTitle(),tipo);
                }
                return false;
            }

        });
        loadMarkers();

        ubicationFinder = new UbicationFinder(this){
            @Override
            public void onLocation(Location location) {
                if (location != null && mMap != null) {
                    Mapa.this.addMyPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    Almacenamiento aux=new Almacenamiento();
                    aux.push(location.getLatitude(),"Jugador/"+FirebaseAuth.getInstance().getUid(),"/latitud/");
                    aux.push(location.getLongitude(),"Jugador/"+FirebaseAuth.getInstance().getUid(),"/longitud/");
                }
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        //navigationView.setNavigationItemSelectedListener(this);
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        ubicationFinder = new UbicationFinder(this){
            @Override
            public void onLocation(Location location) {
                if (location != null && mMap != null) {
                    Mapa.this.addMyPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    Almacenamiento aux=new Almacenamiento();
                    aux.push(location.getLatitude(),"Jugador/"+FirebaseAuth.getInstance().getUid(),"/latitud/");
                    aux.push(location.getLongitude(),"Jugador/"+FirebaseAuth.getInstance().getUid(),"/longitud/");
                }
            }
        };
         ubicationFinder.startLocationUpdates();

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightSensorListener);
        if(myPosition!=null)
            myPosition.remove();
        if (ubicationFinder != null)
            ubicationFinder.stopLocationUpdates();
            new Almacenamiento().erase("Jugador/"+FirebaseAuth.getInstance().getUid()+"/latitud/");
            new Almacenamiento().erase("Jugador/"+FirebaseAuth.getInstance().getUid()+"/longitud/");
            myPosition = null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 666:
                if(resultCode== Activity.RESULT_OK)
                {
                    //Intent intent=new Intent();
                    //String extra=getIntent().getStringExtra("nombreLugar");
                    //intent.putExtra("nombreLugar",extra);
                    setResult(Activity.RESULT_OK,data);
                    finish();
                }
                //you just got back from activity B - deal with resultCode
                //use data.getExtra(...) to retrieve the returned data
                break;
        }
    }

    private void activarSensorDeLuz(){
        lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (mMap != null) {
                    if (event.values[0] < 300) {
                        Log.i("MAPS", "DARK MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Mapa.this, R.raw.style1));
                    }else{
                        Log.i("MAPS", "LIGHT MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Mapa.this, R.raw.style2));
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }


}

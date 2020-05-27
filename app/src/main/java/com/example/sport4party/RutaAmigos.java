package com.example.sport4party;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Utils.Almacenamiento;
import com.example.sport4party.Utils.LocationFinder;
import com.example.sport4party.Utils.TaskLoadedCallback;
import com.example.sport4party.Utils.TraceRute;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RutaAmigos extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private LocationFinder gCoderHandler;
    private Marker myPosition;
    private GoogleMap mMap;
    private UbicationFinder ubicationFinder;
    private Polyline currentPolyline;
    private LatLng posicionEvento;
    private String nombreEvento;
    private HashMap<String, LatLng> eventoAsignado;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private SensorManager sensorManager;
    private ArrayList<Marker> markersViejos;
    private String eventoId;
    Sensor lightSensor;
    SensorEventListener lightSensorListener;
    private String idUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_evento);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapRuta);
        mapFragment.getMapAsync(this);
        nombreEvento = "por definir";
        gCoderHandler = new LocationFinder(RutaAmigos.this);
        myPosition = null;
        database=FirebaseDatabase.getInstance();
        eventoAsignado = new HashMap<String, LatLng>();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        markersViejos = new ArrayList<>();

        Bundle parametros = this.getIntent().getExtras();

        //posicionEvento = new LatLng(4.5927,-74.1379);
        if(parametros != null){
            eventoId = parametros.getString("id");
        }

        Log.i("DATOS NUEVOS", eventoId.toString());

        if(!eventoId.equals("-1")) {
            generarMarkets(eventoId);
        }
        else{
            Toast toast1 = Toast.makeText(getApplicationContext(), "Error de carga, no se encontro el evento", Toast.LENGTH_LONG);
            toast1.show();
        }
        activarSensorDeLuz();



        myRef=database.getReference("Evento");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapShot: dataSnapshot.getChildren()) {
                    if(singleSnapShot.getKey().equals(RutaAmigos.this.eventoId)){
                        HashMap<String, Object> datos = (HashMap<String, Object>) singleSnapShot.getValue();

                        if(datos.containsKey("ubicacion")){
                            String dato = (String) datos.get("ubicacion");
                            RutaAmigos.this.idUbicacion = dato.trim();
                            Log.i("VALOR ASIGNADO -----", RutaAmigos.this.idUbicacion);


                            //------- ESTE ES EL OTRO
                            myRef=database.getReference("Ubicacion");
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot singleSnapShot : dataSnapshot.getChildren()) {
                                        if(singleSnapShot.getKey().equals(RutaAmigos.this.idUbicacion)){
                                            HashMap<String, Object> datos = (HashMap<String, Object>) singleSnapShot.getValue();
                                            RutaAmigos.this.posicionEvento = new LatLng((Double) datos.get("latitud"), (Double) datos.get("Longitud"));
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                            // ----- ESTE ES EL OTRO
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    //_----------------------------------------------------------------------------------------
    private void generarMarkets(final String numEvento) {
        myRef = database.getReference("Jugador");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapShot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> datos = (HashMap<String, Object>) singleSnapShot.getValue();
                    if (datos.containsKey("eventos")) {
                        HashMap<String, String> eventos = (HashMap<String, String>) datos.get("eventos");
                        for (String i : eventos.keySet()) {
                            if (eventos.containsKey(numEvento)) {
                                if (datos.containsKey("latitud") && datos.containsKey("longitud")) {
                                    RutaAmigos.this.eventoAsignado.put((String) datos.get("nombreUsuario"), new LatLng((Double) (datos.get("latitud")), (Double) (datos.get("longitud"))));
                                }
                            }
                        }
                    }

                    if (datos.containsKey("eventosCreados")) {
                        HashMap<String, String> eventos = (HashMap<String, String>) datos.get("eventosCreados");
                        for (String i : eventos.keySet()) {
                            if (eventos.containsKey(numEvento)) {
                                if (datos.containsKey("latitud") && datos.containsKey("longitud")) {
                                    RutaAmigos.this.eventoAsignado.put((String) datos.get("nombreUsuario"), new LatLng((Double) (datos.get("latitud")), (Double) (datos.get("longitud"))));
                                }
                            }
                        }
                    }
                }
                //PRUEBA

                for(Marker i : markersViejos){
                    i.remove();
                }

                for (String i : RutaAmigos.this.eventoAsignado.keySet()) {
                    Marker markerAux = RutaAmigos.this.mMap.addMarker(new MarkerOptions().position(RutaAmigos.this.eventoAsignado.get(i)).title(i));
                    markersViejos.add(markerAux);
                }
                /*for (LatLng i : RutaAmigos.this.eventoAsignado.values()) {
                    Marker markerAux = RutaAmigos.this.mMap.addMarker(new MarkerOptions().position(i).title("AMIGOS"));
                    markersViejos.add(markerAux);
                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    //_----------------------------------------------------------------------------------------


    //MAPA

    public void addMyPosition(LatLng position){
        if(myPosition == null){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
        else{
            myPosition.remove();
        }
        myPosition = mMap.addMarker(new MarkerOptions().position(position).title("Posición actual"));
    }

    public void markRoute(Marker a, Marker b) {
        String url = getUrl(a.getPosition(), b.getPosition(), "driving");
        new TraceRute(RutaAmigos.this).execute(url, "driving");
    }

    public void markerRoute(LatLng a, LatLng b){
        String url = getUrl(a, b, "driving");
        Log.i("TAG", url);
        new TraceRute(RutaAmigos.this).execute(url, "driving");

    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//Obtener por el intent un ID event y lanzarlos todos
        ubicationFinder = new UbicationFinder(this){
            @Override
            public void onLocation(Location location) {
                if (location != null && mMap != null) {


                    if(posicionEvento == null){
                        //Toast.makeText(RutaAmigos.this, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
                    }
                    else{
                        //mMap.clear();
                        Marker marker2 = mMap.addMarker(new MarkerOptions().position(posicionEvento).title("Evento").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));//gCoderHandler.searchFromLocation(posicionEvento, 1).getAddressLine(0)));
                        LatLng actual = new LatLng(location.getLatitude(), location.getLongitude());
                        markerRoute(actual, RutaAmigos.this.posicionEvento);
                        RutaAmigos.this.addMyPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (ubicationFinder != null)
            ubicationFinder.startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightSensorListener);
        if (ubicationFinder != null)
            ubicationFinder.stopLocationUpdates();
        //myPosition = null;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }


    private void activarSensorDeLuz(){
        lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (mMap != null) {
                    if (event.values[0] < 300) {
                        Log.i("MAPS", "DARK MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(RutaAmigos.this, R.raw.style1));
                    }else{
                        Log.i("MAPS", "LIGHT MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(RutaAmigos.this, R.raw.style2));
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
}

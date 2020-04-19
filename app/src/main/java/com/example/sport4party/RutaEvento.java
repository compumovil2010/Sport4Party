package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sport4party.Utils.LocationFinder;
import com.example.sport4party.Utils.TaskLoadedCallback;
import com.example.sport4party.Utils.TraceRute;
import com.example.sport4party.Utils.UbicationFinder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class RutaEvento extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private LocationFinder gCoderHandler;
    private Marker myPosition;
    private GoogleMap mMap;
    private UbicationFinder ubicationFinder;
    private Polyline currentPolyline;
    private LatLng posicionEvento;
    private String nombreEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_evento);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapRuta);
        mapFragment.getMapAsync(this);
        nombreEvento = "por definir";
        gCoderHandler = new LocationFinder(RutaEvento.this);
        myPosition = null;

        Bundle parametros = this.getIntent().getExtras();

        if(parametros != null){
            String datos = parametros.getString("latitud");
            String datos2 = parametros.getString("longitud");
            posicionEvento = new LatLng(Integer.parseInt(datos), Integer.parseInt(datos2));
        }
        else{
            posicionEvento = new LatLng(4.57, -74.13);
        }
    }


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
        new TraceRute(RutaEvento.this).execute(url, "driving");
    }

    public void markerRoute(LatLng a, LatLng b){
        String url = getUrl(a, b, "driving");
        Log.i("TAG", url);
        new TraceRute(RutaEvento.this).execute(url, "driving");

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



        //LatLng actual = new LatLng(4.5796769, -74.1383976);
        //Marker marker1 = mMap.addMarker(new MarkerOptions().position(actual).title(gCoderHandler.searchFromLocation(actual, 1).getAddressLine(0)));
        // futuro = new LatLng(4.724959, -74.074470);
        //Marker marker2 = mMap.addMarker(new MarkerOptions().position(futuro).title(gCoderHandler.searchFromLocation(futuro, 1).getAddressLine(0)));


        ubicationFinder = new UbicationFinder(this){
            @Override
            public void onLocation(Location location) {
                if (location != null && mMap != null) {

                    Marker marker2 = mMap.addMarker(new MarkerOptions().position(posicionEvento).title(gCoderHandler.searchFromLocation(posicionEvento, 1).getAddressLine(0)));

                    if(posicionEvento == null){
                        Toast.makeText(RutaEvento.this, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
                    }
                    else{
                        //mMap.clear();
                        LatLng actual = new LatLng(location.getLatitude(), location.getLongitude());
                        markerRoute(actual, RutaEvento.this.posicionEvento);
                        RutaEvento.this.addMyPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    }
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
        //sensorManager.unregisterListener(lightSensorListener);
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

}

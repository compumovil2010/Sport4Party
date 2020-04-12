package com.example.sport4party.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class UbicationFinder {
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    final int CODIGO_GPS = 9;
    final int REQUEST_CHECK_SETTINGS = 999;
    Activity actividadProveniente;

    ///Constructor personalizado
    public UbicationFinder(Activity actividadProveniente) {
        this.actividadProveniente = actividadProveniente;
        inicializacionDeLocalizacion();
    }

    public LocationRequest getmLocationRequest() {
        return mLocationRequest;
    }

    public void setmLocationRequest(LocationRequest mLocationRequest) {
        this.mLocationRequest = mLocationRequest;
    }

    public LocationCallback getmLocationCallback() {
        return mLocationCallback;
    }

    public void setmLocationCallback(LocationCallback mLocationCallback) {
        this.mLocationCallback = mLocationCallback;
    }

    public FusedLocationProviderClient getmFusedLocationClient() {
        return mFusedLocationClient;
    }

    public void setmFusedLocationClient(FusedLocationProviderClient mFusedLocationClient) {
        this.mFusedLocationClient = mFusedLocationClient;
    }

    public int getCODIGO_GPS() {
        return CODIGO_GPS;
    }

    public int getREQUEST_CHECK_SETTINGS() {
        return REQUEST_CHECK_SETTINGS;
    }

    public Activity getActividadProveniente() {
        return actividadProveniente;
    }


    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000); //tasa de refresco en milisegundos
        mLocationRequest.setFastestInterval(1000); //mÃ¡xima tasa de refresco
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    ///Este metodo es el que se llama desde el objeto
    public void inicializacionDeLocalizacion() {
        requestPermission(actividadProveniente, Manifest.permission.ACCESS_FINE_LOCATION, "Es necesario prender el gps para poder marcar ubicaciones en el mapa", CODIGO_GPS);
        mLocationRequest = createLocationRequest();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(actividadProveniente);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(actividadProveniente);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        initListenersLocalizacion(task);
    }

    public void onLocationUpdate() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    onLocation(location);
                }
            }
        };
    }

    public void onLocation(Location location){
    }

    private void initListenersLocalizacion(Task<LocationSettingsResponse> task) {
        onLocationUpdate();
        task.addOnSuccessListener(actividadProveniente, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates(); //Todas las condiciones para recibir localizaciones
            }
        });
        mFusedLocationClient.getLastLocation().addOnSuccessListener(actividadProveniente, new
                OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.i("LOCATION", "onSuccess location");
                        if (location != null) {

                            location.getLongitude();
                            location.getLatitude();
                            location.getAltitude();
                            Log.i(" LOCATION ",
                                    "Longitud: " + location.getLongitude());
                            Log.i(" LOCATION ", "Latitud: " + location.getLatitude());
                        }
                    }
                });


        task.addOnFailureListener(actividadProveniente, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(actividadProveniente,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    public void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(actividadProveniente,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
    }

    public void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    public void requestPermission(Activity context, String permission, String just, int id) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                Toast.makeText(context, just, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, id);
        }
    }


}
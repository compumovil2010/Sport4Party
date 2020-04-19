package com.example.sport4party.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.List;

public class LocationFinder {
    Geocoder mGeocoder;

    public static final double lowerLeftLatitude = 4.534925;
    public static final double lowerLeftLongitude = -74.333062;
    public static final double upperRightLatitude = 4.836704;
    public static final double upperRigthLongitude = -73.898772;

    public LocationFinder(Context context) {
        this.mGeocoder = new Geocoder(context);
    }

    public Address searchFromLocation(LatLng position, int maxResults) {
        try {
            List<Address> addresses = mGeocoder.getFromLocation(position.latitude, position.longitude, maxResults);
            if (addresses != null && !addresses.isEmpty()) {
                Address addressResult = addresses.get(0);
                return addressResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Address searchFromLocationName(String addressString, int maxResults) {
        try {
            List<Address> addresses = mGeocoder.getFromLocationName(addressString, maxResults,
                    lowerLeftLatitude, lowerLeftLongitude, upperRightLatitude, upperRigthLongitude);
            if (addresses != null && !addresses.isEmpty()) {
                Address addressResult = addresses.get(0);
                return addressResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

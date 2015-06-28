package com.demotask.carsshow.utility;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sumedh on 28/06/15.
 */
public final class MapUtility {

    private MapUtility(){

    }

    public static String getAddressFromCoordinates(Context context, LatLng coordinate){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(coordinate.latitude, coordinate.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            String result = String.format("%s, %s",address, city) ;
            return result ;
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    public static double getDistance(LatLng latlang1, LatLng latlang2){
        Location locationA = new Location("point A");
        locationA.setLatitude(latlang1.latitude);
        locationA.setLongitude(latlang1.longitude);
        Location locationB = new Location("point B");
        locationB.setLatitude(latlang2.latitude);
        locationB.setLongitude(latlang2.longitude);
        return locationA.distanceTo(locationB) ;
    }
}

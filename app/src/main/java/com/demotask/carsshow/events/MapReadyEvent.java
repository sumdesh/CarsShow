package com.demotask.carsshow.events;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Sumedh on 28/06/15.
 */
public class MapReadyEvent {

    public GoogleMap googleMap;


    public MapReadyEvent(GoogleMap gMap){
        this.googleMap = gMap;
    }
}

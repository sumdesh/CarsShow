package com.demotask.carsshow.pojos;

/**
 * Created by Sumedh on 27/06/15.
 */
public class CarLocation {

    public double latitude;
    public double longitude;
    public String carModel;


    public CarLocation(double latitude, double longitude, String carModel){
        this.latitude = latitude;
        this.longitude = longitude;
        this.carModel = carModel;
    }

}

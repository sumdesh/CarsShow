package com.demotask.carsshow.pojos;

/**
 * Created by Sumedh on 27/06/15.
 */
public class CarLocation {

    public String carId;
    public double latitude;
    public double longitude;
    public String carModel;


    public CarLocation(String carId, double latitude, double longitude, String carModel){
        this.carId = carId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.carModel = carModel;
    }

}

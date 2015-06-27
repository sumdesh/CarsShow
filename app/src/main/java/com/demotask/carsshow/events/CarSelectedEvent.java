package com.demotask.carsshow.events;

import com.demotask.carsshow.webservice.Car;

/**
 * Created by Sumedh on 27/06/15.
 */
public class CarSelectedEvent {

    Car selectedCar;

    public CarSelectedEvent(Car car){
        this.selectedCar = car;
    }

}

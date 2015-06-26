package com.demotask.carsshow.webservice;

import android.test.suitebuilder.annotation.LargeTest;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by edrsoftware on 25.06.15.
 */
public interface ICarsService {

    @GET("/cars.json")
    List<Car> listCars();
}

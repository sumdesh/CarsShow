package com.demotask.carsshow.webservice;

import android.test.suitebuilder.annotation.LargeTest;

import java.util.List;

/**
 * Created by edrsoftware on 25.06.15.
 */
public interface ICarsService {

    @GET
    List<Car> listCars();
}

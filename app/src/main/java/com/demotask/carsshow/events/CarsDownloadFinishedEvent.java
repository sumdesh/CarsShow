package com.demotask.carsshow.events;

import com.demotask.carsshow.webservice.Car;

import java.util.List;

/**
 * Created by edrsoftware on 26.06.15.
 */
public class CarsDownloadFinishedEvent {

    public final List<Car> downloadedCarsInfo;

    public CarsDownloadFinishedEvent(List<Car> downloadedCarsInfo) {
        this.downloadedCarsInfo = downloadedCarsInfo;
    }
}

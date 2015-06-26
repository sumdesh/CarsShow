package com.demotask.carsshow.core;

import android.content.Context;

import com.demotask.carsshow.webservice.Car;
import com.demotask.carsshow.webservice.RetrofitAdapterBuilder;
import com.squareup.otto.Bus;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by edrsoftware on 26.06.15.
 */
public class ApplicationState {

    private static ApplicationState instance;

    private static List<Car> downloadedCarsInfo;

    public static ApplicationState getInstance(){
        if (instance == null){
            instance = new ApplicationState();
        }

        return instance;
    }

    private AndroidBus eventBus = new AndroidBus();
    private RestAdapter restAdapter;

    //initialized in GlobalApplicationState
    public void initRestAdapter(Context context) {
        restAdapter = RetrofitAdapterBuilder.buildAdapter(context);
    }

    public Bus getEventBus() {
        return eventBus;
    }

    public void setCarInfo(List<Car> carInfo){
        this.downloadedCarsInfo = carInfo;
    }

    public List<Car>getCarInfo(){
        return downloadedCarsInfo;
    }

    public <T> T getRestService(Class<T> service) {
        return restAdapter.create(service);
    }
}

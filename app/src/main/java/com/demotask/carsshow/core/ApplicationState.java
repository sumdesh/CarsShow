package com.demotask.carsshow.core;

import android.content.Context;

import com.demotask.carsshow.webservice.Car;
import com.demotask.carsshow.webservice.RetrofitAdapterBuilder;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by edrsoftware on 26.06.15.
 */
public class ApplicationState {

    public static final LatLng HOME_LOCATION = new LatLng(48.1333, 11.5667);

    private static ApplicationState instance;
    private static List<Car> downloadedCarsInfo;
    private static HashMap<String, Car> carsMapping;


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
        if (carInfo!=null) {
            carsMapping = new HashMap<String, Car>();
            for (Car item : carInfo) {
                carsMapping.put(item.id, item);
            }
        }
        this.downloadedCarsInfo = carInfo;
    }

    public List<Car>getCarInfo(){
        return downloadedCarsInfo;
    }

    public HashMap<String, Car> getCarMapping(){
        return carsMapping;
    }

    public <T> T getRestService(Class<T> service) {
        return restAdapter.create(service);
    }
}

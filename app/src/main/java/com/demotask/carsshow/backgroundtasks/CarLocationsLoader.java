package com.demotask.carsshow.backgroundtasks;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.demotask.carsshow.core.ApplicationState;
import com.demotask.carsshow.pojos.CarLocation;
import com.demotask.carsshow.webservice.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sumedh on 27/06/15.
 */

public class CarLocationsLoader extends AsyncTaskLoader<List<CarLocation>> {

    private boolean registered = false;
    List<CarLocation> data;

    public CarLocationsLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(List<CarLocation> data) {
        this.data = data;

        // Return whether this load has been reset. That is, either the loader has not yet been started for the first
        // time, or its reset() has been called.
        if (isReset()) {
            reset();
        }

        // Return whether this load has been started. That is, its startLoading() has been called and no calls to
        // stopLoading() or reset() have yet been made.
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    public List<CarLocation> loadInBackground() {
        List<CarLocation> carLocations = new ArrayList<CarLocation>();
        List<Car> carsInfo = ApplicationState.getInstance().getCarInfo();
        if (carsInfo != null) {
            for (Car item : carsInfo) {
                CarLocation location = new CarLocation(item.latitude, item.longitude, item.modelName);
                carLocations.add(location);
            }
            return carLocations;
        }
        return null;
    }

    @Override
    public void onCanceled(List<CarLocation> data) {
        super.onCanceled(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        stopLoading();

        if (this.data != null) {
            this.data = null;
        }
    }

    @Override
    protected void onStartLoading() {

        if (!registered) {
            ApplicationState.getInstance().getEventBus().register(this);
            registered = true;
        }

        if (this.data != null) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();

        if (registered) {
            ApplicationState.getInstance().getEventBus().unregister(this);
            registered = false;
        }
    }
}

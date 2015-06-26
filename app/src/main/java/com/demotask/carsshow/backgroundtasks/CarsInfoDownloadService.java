package com.demotask.carsshow.backgroundtasks;

import android.app.IntentService;
import android.content.Intent;

import com.demotask.carsshow.core.ApplicationState;
import com.demotask.carsshow.events.CarsDownloadFinishedEvent;
import com.demotask.carsshow.webservice.Car;
import com.demotask.carsshow.webservice.ICarsService;
import com.squareup.otto.Bus;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by edrsoftware on 26.06.15.
 */
public class CarsInfoDownloadService extends IntentService {

    private static final String SERVICE_NAME = "CarsInfoDownloadService";

    public CarsInfoDownloadService(){
        super(SERVICE_NAME);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CarsInfoDownloadService(String name) {
        super(name);
    }

    private void getCarsInfo() {
        ApplicationState state = ApplicationState.getInstance();
        Bus eventBus = state.getEventBus();

        ICarsService service = state.getRestService(ICarsService.class);
        List<Car> carsInfo = service.listCars();

        ApplicationState.getInstance().setCarInfo(carsInfo);

        //eventBus.post(new CarsDownloadFinishedEvent(carsInfo));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getCarsInfo();
    }
}

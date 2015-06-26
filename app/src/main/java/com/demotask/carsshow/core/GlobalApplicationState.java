package com.demotask.carsshow.core;

import android.app.Application;

/**
 * Created by edrsoftware on 26.06.15.
 */
public class GlobalApplicationState extends Application{

    private final ApplicationState state = ApplicationState.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        state.initRestAdapter(this);
    }


}

package com.demotask.carsshow.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.demotask.carsshow.backgroundtasks.CarsInfoDownloadService;
import com.demotask.carsshow.core.ApplicationState;

/**
 * Created by Sumedh on 28/06/15.
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getAction();

        if (NetworkUtility.isNetworkAvailable(context)){
            if (ApplicationState.getInstance().getCarInfo() == null){
                //Starting cars info download on background
                Intent service = new Intent(context, CarsInfoDownloadService.class);
                context.startService(service);
            }
        }else{

        }

        Log.d(ConnectivityReceiver.class.getSimpleName(), "action: "
                + intent.getAction());
    }
}

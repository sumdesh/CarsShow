package com.demotask.carsshow.utility;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Sumedh on 28/06/15.
 */
public class NetworkUtility {

    public static boolean isNetworkAvailable(Context context){
        boolean networkConnection = false;
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobile != null && mobile.isConnected()) {
            networkConnection = true;
            //Toast.makeText(context, "mobile network",Toast.LENGTH_SHORT).show();

        } else if (wifi != null && wifi.isConnected()) {
            networkConnection = true;
            //Toast.makeText(context, "wifi network",Toast.LENGTH_SHORT).show();
        } else {
            //LOG.info("No Network");
        }

        return networkConnection;
    }

}

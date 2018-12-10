package com.example.muhammadfakhar.pro;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.muhammadfakhar.pro.Service.CheckConnection;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    private static ConnectivityChangeReceiver INSTANCE = null;

    // other instance variables can be here

    private ConnectivityChangeReceiver() {}

    public static ConnectivityChangeReceiver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectivityChangeReceiver();
        }
        return(INSTANCE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        ComponentName comp = new ComponentName(context.getPackageName(),
                CheckConnection.class.getName());
        intent.putExtra("isNetworkConnected", isConnected(context));
        context.startService(intent.setComponent(comp));
    }

    // to check internet connection
    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
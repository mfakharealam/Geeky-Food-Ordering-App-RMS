package com.example.muhammadfakhar.pro.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.muhammadfakhar.pro.ToastDisplay;

public class CheckConnection extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private Handler handler;
    public CheckConnection(String name) {
        super(name);
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        boolean isNetworkConnected = extras.getBoolean("isNetworkConnected");
        // your code
        if (isNetworkConnected)
        {
            handler.post(new ToastDisplay(this, "Internet is Connected!"));
        }
        else
        {
            handler.post(new ToastDisplay(this, "No Internet Connection!"));
        }
    }

}

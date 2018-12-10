package com.example.muhammadfakhar.pro.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class CheckConnection extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CheckConnection(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        boolean isNetworkConnected = extras.getBoolean("isNetworkConnected");
        // your code
        if (isNetworkConnected)
        {
            Toast.makeText(CheckConnection.this, "Connected!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(CheckConnection.this, "No Connection!", Toast.LENGTH_SHORT).show();
        }
    }

}

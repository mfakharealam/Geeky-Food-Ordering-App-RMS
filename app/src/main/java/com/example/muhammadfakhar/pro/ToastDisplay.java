package com.example.muhammadfakhar.pro;

import android.content.Context;
import android.widget.Toast;

public class ToastDisplay implements Runnable {
    private final Context mContext;
    String mText;

    public ToastDisplay(Context mContext, String text){
        this.mContext = mContext;
        mText = text;
    }

    public void run(){
        Toast.makeText(mContext, mText, Toast.LENGTH_SHORT).show();
    }
}
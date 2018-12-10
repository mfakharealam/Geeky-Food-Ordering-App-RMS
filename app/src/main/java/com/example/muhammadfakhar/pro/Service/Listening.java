package com.example.muhammadfakhar.pro.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.muhammadfakhar.pro.FoodItem;
import com.example.muhammadfakhar.pro.FoodOrders;
import com.example.muhammadfakhar.pro.OrderDeliveryStatus;
import com.example.muhammadfakhar.pro.OrderDetail;
import com.example.muhammadfakhar.pro.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Listening extends Service implements ChildEventListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public Listening() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ///
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Food Orders");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        databaseReference.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        ////
        FoodOrders foodOrders = dataSnapshot.getValue(FoodOrders.class);
        notifyUser(dataSnapshot.getKey(), foodOrders);

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }


    private void notifyUser(String key, FoodOrders foodOrders) {
        Intent intent = new Intent(getBaseContext(), OrderDeliveryStatus.class);
        intent.putExtra("User Phone", foodOrders.getPhone());
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Geeky Foods")
                .setContentInfo("Order Delivery Status Updated!")
                .setContentText("Order No. " + key + " Delivery Status updated to " + foodOrders.getDeliveryStatus())
                .setContentIntent(pendingIntent)
                .setContentInfo("Status")
                .setSmallIcon(R.mipmap.ic_launcher);
        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int)System.currentTimeMillis(), builder.build());

    }
}

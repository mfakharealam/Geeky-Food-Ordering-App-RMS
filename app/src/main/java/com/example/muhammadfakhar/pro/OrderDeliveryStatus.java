package com.example.muhammadfakhar.pro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderDeliveryStatus extends AppCompatActivity { // it's basically for drawer's orders button

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<FoodOrders, OrderViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_delivery_status);
        ////
        getSupportActionBar().setTitle("Orders History");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("FoodOrders");

        recyclerView = findViewById(R.id.orderListRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(OrderDeliveryStatus.this);
        recyclerView.setLayoutManager(layoutManager);

        showAllOrders(UserInstance.currUser.getPhone());
    }

    private void showAllOrders(final String phone)
    {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FoodOrders, OrderViewHolder>(
                FoodOrders.class, R.layout.order_layout, OrderViewHolder.class,
                databaseReference.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, FoodOrders model, final int position) {
                viewHolder.getOrderIdTv().setText(firebaseRecyclerAdapter.getRef(position).getKey());
                viewHolder.getOrderStatusTv().setText("Status: " + model.getDeliveryStatus());
                viewHolder.getUserAddrTv().setText("Address: " + model.getAddress());
                viewHolder.getUserPhoneTv().setText("Phone: " + model.getPhone());
                viewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (firebaseRecyclerAdapter.getItem(position).getDeliveryStatus()
                                .equals("Processing..."))
                        {
                            deleteOrder(firebaseRecyclerAdapter.getRef(position).getKey());
                        }
                        else {
                            Toast.makeText(OrderDeliveryStatus.this, "Order cannot be deleted now.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void deleteOrder(final String key) {
        databaseReference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(OrderDeliveryStatus.this, "Order " + key + " deleted.",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OrderDeliveryStatus.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

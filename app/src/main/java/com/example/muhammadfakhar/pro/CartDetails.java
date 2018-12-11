package com.example.muhammadfakhar.pro;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class CartDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView textView;
    private FButton placeOrderBtn;
    private List<OrderDetail> cartList;
    private CartAdapter cartAdapter;
    private User currUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        ///
        getSupportActionBar().setTitle("Cart Details");
        currUser = UserInstance.currUser;
        cartList = new ArrayList<>();
       // firebaseDatabase = FirebaseDatabase.getInstance();
        //databaseReference = firebaseDatabase.getReference("FoodOrders");

        recyclerView = findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        textView = findViewById(R.id.totalAmount);
        placeOrderBtn = findViewById(R.id.placeOrderBtn);
        placeOrderBtn.setButtonColor(Color.parseColor("#ffffff"));
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList.isEmpty())
                    Snackbar.make(v, "First Order Something!", Snackbar.LENGTH_LONG).show();
                else
                {
                    Intent intent = new Intent(CartDetails.this, PaymentMethod.class);
                    startActivity(intent);
                    //alertDialogForAddress();
                }
            }
        });
        showOrdersList();
    }
    private void showOrdersList()
    {
        cartList = new Database(this).getOrderedItems();
        cartAdapter = new CartAdapter(cartList, this);
        recyclerView.setAdapter(cartAdapter);
        int total = 0;
        for (OrderDetail orderDetail:cartList)
        {
            total += (Integer.parseInt(orderDetail.getPrice())) * (Integer.parseInt(orderDetail.getQuantity()));
        }
        textView.setText(String.valueOf(total));
    }

}

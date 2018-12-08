package com.example.muhammadfakhar.pro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListFoodActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference foodRef;
    private TextView nameTv;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rVLayoutManager;
    private String cuisineId;
    private FirebaseRecyclerAdapter<FoodItem, FoodViewHolder> firebaseRecyclerAdapter, searchAdapter;

    ///
    private MaterialSearchBar materialSearchBar;
    private List<String> suggestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        ////
        getSupportActionBar().setTitle("Foods");
        firebaseDatabase = FirebaseDatabase.getInstance();
        foodRef = firebaseDatabase.getReference("Foods");
        recyclerView = findViewById(R.id.rec_food_list);
        recyclerView.setHasFixedSize(true);
        rVLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rVLayoutManager);
        materialSearchBar = findViewById(R.id.searchFBar);
        materialSearchBar.setCardViewElevation(15);
        Intent intent = getIntent();
        cuisineId = intent.getStringExtra("Cuisine Id");
        
        if (cuisineId != null)
        {
            listFood(cuisineId);
        }
        
        ////
        suggestionsList();
        materialSearchBar.setLastSuggestions(suggestions);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> newSuggestions = new ArrayList<>();
                for (String searched: suggestions)
                {
                    if (searched.toLowerCase().contains(materialSearchBar.getText().toString()))
                    {
                        newSuggestions.add(searched);
                    }
                }
                materialSearchBar.setLastSuggestions(newSuggestions);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                {
                    recyclerView.setAdapter(firebaseRecyclerAdapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                showSuggestions(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
    }

    private void showSuggestions(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<FoodItem, FoodViewHolder>(FoodItem.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodRef.orderByChild("Name").equalTo(text.toString()))// select * from food where cuisine = menuId...
        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, FoodItem model, int position) {
                viewHolder.foodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final FoodItem curr = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(ListFoodActivity.this, FoodDetails.class);
                        intent.putExtra("Food Id", searchAdapter.getRef(position).getKey()); // view click is actually food Id
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);
    }

    private void suggestionsList() {
        foodRef.orderByChild("MenuId").equalTo(cuisineId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    FoodItem foodItem = dataSnapshot1.getValue(FoodItem.class);
                    suggestions.add(foodItem.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listFood(String cuisineId) {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FoodItem, FoodViewHolder>(FoodItem.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodRef.orderByChild("MenuId").equalTo(cuisineId))// select * from food where cuisine = menuId...
        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, FoodItem model, int position) {
                viewHolder.foodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final FoodItem curr = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(ListFoodActivity.this, FoodDetails.class);
                        intent.putExtra("Food Id", firebaseRecyclerAdapter.getRef(position).getKey()); // view click is actually food Id
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}

package com.example.muhammadfakhar.pro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ActivityGallery extends AppCompatActivity {

    private ListView list;
    private GalleryAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ListView(this);
        list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        adapter = new GalleryAdapter(this);
        list.setAdapter(adapter); // setting customized array adapter to a customized list view
        registerForContextMenu(list);
        createView();
    }

    private void createView(){ // to show the UI
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
        //layout.addView(createText());
        layout.addView(list);
        setContentView(layout);
    }
}

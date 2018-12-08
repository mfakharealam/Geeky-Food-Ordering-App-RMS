package com.example.muhammadfakhar.pro;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class GalleryAdapter extends BaseAdapter {
    static class ViewHolder {
        ImageView imageView;
        ImageButton shareBtn;
    }
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.food1, R.drawable.food2,
            R.drawable.food3, R.drawable.food4,
            R.drawable.food5, R.drawable.food6,
            R.drawable.food7, R.drawable.food8
    };

    String[] names = {
            "Pancake With Sliced Strawberry", "Food Salad",
            "Vegetable Food", "Seasonings",
            "Two Potatoes", "Chocolate Cake",
            "Meat Burger", "Grilled Chicken"
    };

    // Constructor
    public GalleryAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.activity_gallery, parent, false);
        }
        else
        {
            holder = (GalleryAdapter.ViewHolder) convertView.getTag();
        }
        holder = new GalleryAdapter.ViewHolder();
        holder.imageView = convertView.findViewById(R.id.imageView);
        holder.shareBtn = convertView.findViewById(R.id.shareBtn);
        holder.imageView.setImageResource(mThumbIds[position]);
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // holder.imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(mContext, names[position], Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = mContext.getResources();
                Uri uri = new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(resources.getResourcePackageName(mThumbIds[position]))
                        .appendPath(resources.getResourceTypeName(mThumbIds[position]))
                        .appendPath(resources.getResourceEntryName(mThumbIds[position]))
                        .build();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                mContext.startActivity(Intent.createChooser(intent , "Share"));
            }
        });
        return convertView;
    }

}

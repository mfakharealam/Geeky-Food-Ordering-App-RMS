package com.example.muhammadfakhar.pro;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderViewHolder extends RecyclerView.ViewHolder{

    private TextView orderIdTv, orderStatusTv, userAddrTv, userPhoneTv;
    private ImageView imageView;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderIdTv = itemView.findViewById(R.id.orderId);
        orderStatusTv = itemView.findViewById(R.id.orderStatus);
        userAddrTv = itemView.findViewById(R.id.userAddrOrder);
        imageView = itemView.findViewById(R.id.deleteOrderBtn);
        userPhoneTv = itemView.findViewById(R.id.userPhoneOrder);

    }

    public TextView getOrderIdTv() {
        return orderIdTv;
    }


    public TextView getOrderStatusTv() {
        return orderStatusTv;
    }


    public TextView getUserAddrTv() {
        return userAddrTv;
    }


    public TextView getUserPhoneTv() {
        return userPhoneTv;
    }

    public ImageView getImageView() {
        return imageView;
    }
}

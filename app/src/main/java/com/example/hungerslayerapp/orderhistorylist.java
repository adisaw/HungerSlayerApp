package com.example.hungerslayerapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.ArrayList;

public class orderhistorylist extends ArrayAdapter<String> {

    int id[];
    int [] price;
    String []date;
    String []info;
    String []orderstatus;
    Activity context;


    public orderhistorylist(Activity context,int[] id, String[] orderstatus, int [] price, String[] date, String[] info) {
        super(context,R.layout.layout_orderhistory,date);
        this.context=context;
        this.orderstatus=orderstatus;
        this.price=price;
        this.id=id;
        this.date=date;
        this.info=info;
    }



    @Override
    public View getView(final int position , @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r=convertView;
        final ViewHolder viewHolder;
        if(r==null)
        {
            LayoutInflater layoutInflater =context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout_orderhistory,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)r.getTag();
        }

        viewHolder.tvw1.setText("Order ID: "+Integer.toString(id[position]));
        viewHolder.tvw2.setText("Order Status: "+orderstatus[position]);
        viewHolder.tvw3.setText("Date :"+date[position]);
        viewHolder.tvw4.setText("Total Cost : Rs "+price[position]);
        viewHolder.tvw5.setText(info[position]);

        return r;
    }


    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        TextView tvw4;
        TextView tvw5;



        ViewHolder(View v)
        {
            tvw1=(TextView)v.findViewById(R.id.orderID);
            tvw2=(TextView)v.findViewById(R.id.status);
            tvw3=(TextView)v.findViewById(R.id.Date);
            tvw4=(TextView)v.findViewById(R.id.Price);
            tvw5=(TextView)v.findViewById(R.id.Info);

        }


    }





}

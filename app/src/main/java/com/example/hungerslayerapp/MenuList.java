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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;

public class MenuList extends ArrayAdapter<String> {

    String [] name;
    String [] desc;
    String [] imgUrl;
    int [] id;
    int [] price;
    Activity context;
    Bitmap bitmap;
    public MenuList(Activity context,int[] id, String[] name, int [] price, String[] desc, String[] imgUrl) {
        super(context,R.layout.layout_menu,name);
        this.context=context;
        this.name=name;
        this.price=price;
        this.id=id;
        this.imgUrl=imgUrl;
        this.desc=desc;

    }

    @Override
    public View getView(int postion , @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null)
        {
            LayoutInflater layoutInflater =context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout_menu,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)r.getTag();
        }
        viewHolder.tvw1.setText(name[postion]);
        viewHolder.tvw2.setText(desc[postion]);
        new GetImageFromUrl(viewHolder.ivw).execute(imgUrl[postion]);

        return r;
    }

    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        ImageView ivw;

        ViewHolder(View v)
        {
            tvw1=(TextView)v.findViewById(R.id.Food);
            tvw2=(TextView)v.findViewById(R.id.Descript);
            ivw=(ImageView)v.findViewById(R.id.foodimg);
        }


    }
    public  class GetImageFromUrl extends AsyncTask<String, Void , Bitmap>
    {
        ImageView imgView;

        public  GetImageFromUrl(ImageView imgv) {
            this.imgView=imgv;
        }


        @Override
        protected Bitmap doInBackground(String...url)
        {
            String urldisplay=url[0];
            bitmap=null;
            try
            {
                InputStream ist=new java.net.URL(urldisplay).openStream();
                bitmap= BitmapFactory.decodeStream(ist);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }


    }

}

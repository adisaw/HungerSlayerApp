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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.ArrayList;

public class OrderList extends BaseAdapter {


    Activity context;
    Bitmap bitmap;
    ArrayList <Product> listProducts= new ArrayList<>();
    public OrderList(Activity context, ArrayList<Product> temp) {

        this.context=context;
        this.listProducts=temp;

    }
    @Override
    public int getCount() {
        return listProducts.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public Product getItem(int position) {
        return listProducts.get(position);
    }



    @Override
    public View getView(final int position , @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r=convertView;
        final ViewHolder viewHolder;
        if(r==null)
        {
            LayoutInflater layoutInflater =context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout_orderdetails,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)r.getTag();
        }
        final Product products=getItem(position);
        viewHolder.tvw1.setText(products.Name);

        String temp="Rs "+Integer.toString(products.Price);
        viewHolder.tvw2.setText(temp);
        viewHolder.tvw3.setText("Quantity "+products.cartQuantity+" ");
        new GetImageFromUrl(viewHolder.ivw).execute(products.ImgUrl);

        return r;
    }


    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        ImageView ivw;


        ViewHolder(View v)
        {
            ivw=(ImageView)v.findViewById(R.id.foodimg);
            tvw1=(TextView)v.findViewById(R.id.Food);
            tvw2=(TextView)v.findViewById(R.id.Price);
            tvw3=(TextView)v.findViewById(R.id.Quantity);

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

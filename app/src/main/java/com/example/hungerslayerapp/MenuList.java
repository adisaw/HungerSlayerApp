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

public class MenuList extends ArrayAdapter<String> {

    String [] name;
    String [] desc;
    String [] imgUrl;
    int [] id;
    int [] price;
    Activity context;
    Bitmap bitmap;
    ArrayList <Product> listProducts= new ArrayList<>();
    public MenuList(Activity context,int[] id, String[] name, int [] price, String[] desc, String[] imgUrl, ArrayList<Product> temp) {
        super(context,R.layout.layout_menu,name);
        this.context=context;
        this.name=name;
        this.price=price;
        this.id=id;
        this.imgUrl=imgUrl;
        this.desc=desc;
        this.listProducts=temp;

    }

    public int getcount() {
        return listProducts.size();
    }


    public Product getitem(int position) {
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
            r=layoutInflater.inflate(R.layout.layout_menu,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)r.getTag();
        }
        final Product products=getitem(position);
        viewHolder.tvw1.setText(name[position]);
        viewHolder.tvw2.setText(desc[position]);
        String temp="Rs "+Integer.toString(price[position]);
        viewHolder.tvw3.setText(temp);
        viewHolder.et1.setText(products.cartQuantity+" ");
        new GetImageFromUrl(viewHolder.ivw).execute(imgUrl[position]);
        viewHolder.ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuantity(position,viewHolder.et1,1);
            }
        });
        viewHolder.ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuantity(position,viewHolder.et1,-1);
            }
        });


        return r;
    }
    private void updateQuantity(int position, EditText edTextQuantity, int value) {

        Product products = getitem(position);
        if(value > 0)
        {
            products.cartQuantity = products.cartQuantity + 1;
        }
        else
        {
            if(products.cartQuantity > 0)
            {
                products.cartQuantity = products.cartQuantity - 1;
            }

        }
        edTextQuantity.setText(products.cartQuantity+"");
    }

    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        ImageView ivw;
        Button ib1;
        Button ib2;
        EditText et1;

        ViewHolder(View v)
        {
            tvw1=(TextView)v.findViewById(R.id.Food);
            tvw2=(TextView)v.findViewById(R.id.Descript);
            tvw3=(TextView)v.findViewById(R.id.Price);
            ivw=(ImageView)v.findViewById(R.id.foodimg);
            ib1=(Button)v.findViewById(R.id.ib_plus);
            ib2=(Button)v.findViewById(R.id.ib_minus);
            et1=(EditText)v.findViewById(R.id.editTxt);
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

package com.example.hungerslayerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;


public class orderbackground extends AsyncTask<String,Void, String>  {

    Context context;

    String email,Address,orderStatus="Order Placed";
    String orderID;
    String price;
    String date;
    String address;
    AlertDialog dialog;

    public orderbackground(Context context)
    {
        this.context=context;
    }
    @Override
    protected void onPreExecute() {



    }

    @Override
    protected void onPostExecute(String s) {

        Toast.makeText(context,s,Toast.LENGTH_LONG).show();

    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
            orderID=voids[0];
            email=voids[1];
            price=voids[2];
            orderStatus=voids[3];
            date=voids[4];
            address=voids[5];
            String connstr = "http://192.168.1.34:8089/enterOrder.php";

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
                String data = URLEncoder.encode("orderID", "UTF-8") + "=" + URLEncoder.encode(orderID, "UTF-8") +
                        "&&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+ "&&" +
                        URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8") +"&&"+
                        URLEncoder.encode("orderStatus", "UTF-8") + "=" + URLEncoder.encode(orderStatus, "UTF-8") +"&&"+
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")+"&&"+
                        URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, StandardCharsets.ISO_8859_1));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;
            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();

            }




       return result;
    }
}

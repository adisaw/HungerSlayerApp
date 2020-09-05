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


public class orderitembackground extends AsyncTask<String,Void, String>  {

    Context context;


    String orderID;
    String ID;
    String Quantity;
    AlertDialog dialog;

    public orderitembackground(Context context)
    {
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        //dialog=new AlertDialog.Builder(context).create();
        //dialog.setTitle("Status");


    }

    @Override
    protected void onPostExecute(String s) {
        //dialog.setMessage(s);
        //dialog.show();

    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        orderID=voids[0];
        ID=voids[1];
        Quantity=voids[2];

        String connstr = "http://your ipv4 address:8089/enterOrderItem.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
            String data = URLEncoder.encode("orderID", "UTF-8") + "=" + URLEncoder.encode(orderID, "UTF-8") +
                    "&&" + URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8")+ "&&" +
                    URLEncoder.encode("Quantity", "UTF-8") + "=" + URLEncoder.encode(Quantity, "UTF-8");
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

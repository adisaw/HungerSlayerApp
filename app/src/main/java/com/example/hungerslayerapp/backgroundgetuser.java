package com.example.hungerslayerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class backgroundgetuser extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog dialog;
    String result=null;
    String line=null;
    public backgroundgetuser(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog=new AlertDialog.Builder(context).create();
        dialog.setTitle("Get User Info Status");

    }

    @Override
    protected void onPostExecute(String s) {
        dialog.setMessage(s);
       // dialog.show();
        Intent intent_name = new Intent();
        intent_name.setClass(context, Home.class);
        context.startActivity(intent_name);
    }
    @Override
    protected String doInBackground(String... voids) {

        String email = voids[0];
        String pass = voids[1];

        try {
            URL url = new URL("http://192.168.1.34:8089/getuser.php");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") +
                    "&&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            BufferedInputStream ips = new BufferedInputStream(http.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(ips));

            StringBuilder sb=new StringBuilder();
            while((line=br.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            ips.close();
            result=sb.toString();


            try {
                JSONArray ja = new JSONArray(result);
                JSONObject jo = ja.getJSONObject(0);
                String emailgot = jo.getString("EmailID");
                String namegot = jo.getString("Name");
                String passgot = jo.getString("Password");
                String phonegot = jo.getString("MobileNo");

                User user = new User(emailgot, namegot, passgot, phonegot);
                Common.currentUser = user;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            br.close();
            http.disconnect();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}

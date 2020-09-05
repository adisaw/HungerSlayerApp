package com.example.hungerslayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Menu extends AppCompatActivity {


    String urladdress="http://10.0.2.2:8089/menudisp.php";
    int []id;
    String[] name;
    String []desc;
    int[] price;
    String []imgurl;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    ArrayList <Product> products =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView=(ListView)findViewById(R.id.lview);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        MenuList menuList=new MenuList(this,id,name,price,desc,imgurl,products);
        listView.setAdapter(menuList);



    }
    private void collectData()
    {
        try
        {
            URL url=new URL(urladdress);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while((line=br.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


        try
        {
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;
            id=new int[ja.length()];
            name=new String[ja.length()];
            desc=new String[ja.length()];
            price=new int[ja.length()];
            imgurl=new String[ja.length()];

            for(int i=0;i<=ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                id[i]=jo.getInt("ID");
                name[i]=jo.getString("Name");
                desc[i]=jo.getString("Description");
                price[i]=jo.getInt("Price");
                imgurl[i]=jo.getString("Url");
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

}

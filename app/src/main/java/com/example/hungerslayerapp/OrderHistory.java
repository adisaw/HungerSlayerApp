package com.example.hungerslayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.hungerslayerapp.Common.currentUser;

public class OrderHistory extends AppCompatActivity {

    BufferedInputStream is;
    String line="";
    String result="";
    int []id;
    int []price;
    int []quantity;
    String []email;
    String []name;
    String []orderstatus;
    String []date;
    int []fid;
    int []fprice;

    String []info;
    String []forderstatus;
    String []fdate;
    ListView listView;
    int flag=-1;
    orderhistorylist templist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        collectorder();
        if(flag!=1){
            listView = (ListView) findViewById(R.id.listview);
            StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
            templist = new orderhistorylist(this, fid, forderstatus, fprice, fdate, info);
            listView.setAdapter(templist);
        }
        else
        {
            AlertDialog dialog=new AlertDialog.Builder(this).create();
            dialog.setMessage("No Order History");
            dialog.show();
        }
    }
    public void collectorder()
    {
        try
        {
            URL url=new URL("http://192.168.1.34:8089/orderhistory.php");
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

        if(result.equals("No Record"))
        {
            flag=1;
        }
        else
            {
            try {
                JSONArray ja = new JSONArray(result);
                JSONObject jo = null;
                id = new int[ja.length()];
                name = new String[ja.length()];
                email = new String[ja.length()];
                price = new int[ja.length()];
                orderstatus = new String[ja.length()];
                quantity = new int[ja.length()];
                date = new String[ja.length()];

                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    id[i] = jo.getInt("OrderID");
                    name[i] = jo.getString("Name");
                    price[i] = jo.getInt("TotalPrice");
                    email[i] = jo.getString("emailID");
                    orderstatus[i] = jo.getString("OrderStatus");
                    quantity[i] = jo.getInt("Quantity");
                    date[i] = jo.getString("Date");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            checkorder();
        }

    }
    public  void checkorder()
    {
        if(email==null)
        {
            flag=1;
        }
        else
        {


            if(email.length!=0) {
                int current = id[0];
                int count = 0;
                for (int i = 0; i < email.length; i++) {
                    if (i == 0 && email[i].equals(Common.currentUser.getEmailID())) {
                        count++;
                        current = id[0];
                    } else if (id[i] != current) {
                        current = id[i];
                        if (email[i].equals(Common.currentUser.getEmailID())) {
                            count++;
                        }
                    }
                }
                if (count > 0) {
                    fid = new int[count];
                    fprice = new int[count];
                    forderstatus = new String[count];
                    info = new String[count];
                    fdate = new String[count];
                    int k = 0;
                    int temp = 0;
                    for (int i = 0; i < email.length; i++) {
                        if (email[i].equals(Common.currentUser.getEmailID())) {
                            if (i == 0) {
                                fid[k] = id[0];
                                fprice[k] = price[0];
                                forderstatus[k] = orderstatus[0];
                                fdate[k] = date[0];
                                info[k] = name[0] + " x " + quantity[0] + "\n";
                                temp = 1;
                            } else if (id[i] == id[i - 1]) {
                                info[k] = info[k] + name[i] + " x " + quantity[i] + "\n";
                            } else if (id[i] != id[i - 1]) {
                                if (temp == 1) {
                                    k++;
                                }

                                if (k == 0) {
                                    temp = 1;
                                }

                                fid[k] = id[i];
                                fprice[k] = price[i];
                                forderstatus[k] = orderstatus[i];
                                fdate[k] = date[i];
                                info[k] = name[i] + " x " + quantity[i] + "\n";


                            }
                        }
                    }

                } else {
                    flag = 1;
                }
            }
        }
    }
}

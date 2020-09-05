package com.example.hungerslayerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import  java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.hungerslayerapp.Common.currentUser;

public class OrderDetails extends AppCompatActivity {

    ListView lvSummary;
    TextView tvTotal;
    int Total=0;
    ArrayList<Product> productOrders = new ArrayList<>();
    Bitmap bitmap;
    Button placeOrder;
    EditText Addresstxt;
    String address;
    String email=currentUser.getEmailID();
    String status="Order Placed";
    Date date;
    int orderNumber=1;
    BufferedInputStream is;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        lvSummary = findViewById(R.id.lvOrderDetails);
        tvTotal = findViewById(R.id.tvTotal);
        placeOrder=findViewById(R.id.btnMakePayment);
        Addresstxt=findViewById(R.id.editTxt);


        getOrderItemData();
    }

    private void getOrderItemData() {
        Bundle extras = getIntent().getExtras();
        if(extras !=null )
        {
            String orderItems = extras.getString("orderItems",null);
            if(orderItems!=null && orderItems.length()>0)
            {
                try{
                    JSONArray jsonOrderItems = new JSONArray(orderItems);
                    for(int i=0;i<jsonOrderItems.length();i++)
                    {
                        JSONObject jsonObject = new JSONObject(jsonOrderItems.getString(i));
                        Product product = new Product(
                                jsonObject.getString("ProductName")
                                ,jsonObject.getInt("ProductID")
                                ,jsonObject.getInt("ProductPrice")
                                ,jsonObject.getString("ImageUrl")
                        );
                        product.cartQuantity = jsonObject.getInt("CartQuantity");
                        /* Calculate Total */
                        Total = Total + (product.cartQuantity * product.Price);
                        productOrders.add(product);
                    }

                    if(productOrders.size() > 0)
                    {
                        OrderList orderList = new OrderList(this,productOrders);
                        lvSummary.setAdapter(orderList);
                        tvTotal.setText("Order Total: Rs "+Total);
                    }

                }
                catch (Exception e)
                {
                    showMessage(e.toString());
                }
            }

        }
        else
        {
            showMessage("Cart is Empty.");
        }
    }

    public void orderEntry(View view)
    {
        address=Addresstxt.getText().toString();
        String tempdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String line="",result="";
        try
        {
            URL url=new URL("http://192.168.1.34:8089/getorderid.php");
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
            jo=ja.getJSONObject(0);
            orderNumber=jo.getInt("max(OrderID)");
            orderNumber++;



        }
        catch (Exception ex)
        {
            //orderNumber=1;
            ex.printStackTrace();
        }

        if(Total!=0) {
            orderbackground bg = new orderbackground(this);
            bg.execute(Integer.toString(orderNumber), email, Integer.toString(Total), status, tempdate, address);


        }
        else
        {
            showMessage("Cart is empty.");
        }
        for(int i=0;i<productOrders.size();i++)
        {
            orderitembackground obg=new orderitembackground(this);
            obg.execute(Integer.toString(orderNumber),Integer.toString(productOrders.get(i).Id),Integer.toString(productOrders.get(i).cartQuantity));
        }


        Intent intent=new Intent(this,Home.class);
        startActivity(intent);

    }

    public void showMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
package com.example.hungerslayerapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.StrictMode;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtFullName,txtEmail,txtPhone;
    String urladdress="http://192.168.1.34:8089/menudisp.php";
    int []id;
    String[] name;
    String []desc;
    int[] price;
    String []imgurl;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    ArrayList <Product> products=new ArrayList<>();
    ArrayList <Product> productOrders =new ArrayList<>();
    ArrayList<String> lOrderItems=new ArrayList<>();
    Button btnPlaceOrder;
    MenuList menuList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               placeOrder();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View headerView =navigationView.getHeaderView(0);
        txtFullName=(TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getname());
        txtEmail=(TextView)headerView.findViewById(R.id.txtEmail);
        txtEmail.setText(Common.currentUser.getEmailID());
        txtPhone=(TextView)headerView.findViewById(R.id.txtPhone);
        txtPhone.setText(Common.currentUser.getMobileNo());


        btnPlaceOrder=(Button)findViewById(R.id.btnPlaceOrder);
        listView=(ListView)findViewById(R.id.lview);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        collectData();
        menuList=new MenuList(this,id,name,price,desc,imgurl,products);
        listView.setAdapter(menuList);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

    }
    public void openSummary(String orderItems)
    {
        Intent summaryIntent = new Intent(this,OrderDetails.class);
        summaryIntent.putExtra("orderItems",orderItems);
        startActivity(summaryIntent);
    }
    public void placeOrder()
    {
        productOrders.clear();
        lOrderItems.clear();
        for(int i=0;i<menuList.listProducts.size();i++)
        {
            if(menuList.listProducts.get(i).cartQuantity > 0)
            {
                Product products = new Product(
                        menuList.listProducts.get(i).Name
                        ,menuList.listProducts.get(i).Id
                        ,menuList.listProducts.get(i).Price
                        ,menuList.listProducts.get(i).ImgUrl
                );
                products.cartQuantity = menuList.listProducts.get(i).cartQuantity;
                productOrders.add(products);
                //showMessage("Total Item: "+productOrders.size());
                lOrderItems.add(products.getJsonObject().toString());
            }
        }
        /* Convert String ArrayList into JSON Array */
        JSONArray jsonArray = new JSONArray(lOrderItems);
        /* Open Summary with JSONArray String */
        openSummary(jsonArray.toString());

    }
    public void showMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
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

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                id[i]=jo.getInt("ID");
                name[i]=jo.getString("Name");
                desc[i]=jo.getString("Description");
                price[i]=jo.getInt("Price");
                imgurl[i]=jo.getString("Url");
                products.add(new Product(name[i],id[i],price[i],imgurl[i]));
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_cart) {
            placeOrder();

        } else if (id == R.id.nav_orders) {
            Intent history = new Intent(this,OrderHistory.class);
            startActivity(history);
        } else if (id == R.id.nav_sign_out) {
            //finish();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.example.hungerslayerapp;

import org.json.JSONObject;

public class Product {

    String Name,ImgUrl;
    int Id,  Price, cartQuantity=0;

    public Product(String name, int id, int price, String imgurl )
    {
        Id=id;
        Name=name;
        Price=price;
        ImgUrl=imgurl;


    }
    public String getJsonObject() {
        JSONObject cartItems = new JSONObject();
        try
        {
            cartItems.put("ProductName", Name);
            cartItems.put("ProductID", Id);
            cartItems.put("ProductPrice",Price);
            cartItems.put("ImageUrl",ImgUrl);
            cartItems.put("CartQuantity",cartQuantity);
        }
        catch (Exception e) {}
        return cartItems.toString();
    }



}

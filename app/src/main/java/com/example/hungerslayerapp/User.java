package com.example.hungerslayerapp;

public class User {
    private String Name,Password,EmailID,MobileNo;

    public User()
    {

    }
    public User(String emailID,String name, String password, String mobileNo )
    {
        Name=name;
        Password=password;
        EmailID=emailID;
        MobileNo=mobileNo;
    }
    public String getname()
    {
        return Name;
    }
    public String getMobileNo()
    {
        return MobileNo;
    }
    public String getEmailID()
    {
        return EmailID;
    }
}

package com.example.hungerslayerapp;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtPhone, edtName, edtPassword,edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail=(MaterialEditText)findViewById(R.id.email);
        edtName=(MaterialEditText)findViewById(R.id.name);
        edtPhone=(MaterialEditText)findViewById(R.id.phone);
        edtPassword=(MaterialEditText)findViewById(R.id.password);

    }

    public void btnSignUp(View view)
    {
        String emailID=edtEmail.getText().toString();
        String password=edtPassword.getText().toString();
        String name=edtName.getText().toString();
        String phone=edtPhone.getText().toString();
        String type="SignUp";

        //Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        background bg=new background(this);
        bg.execute(type,emailID,name,password,phone);
    }

}

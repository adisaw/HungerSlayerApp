package com.example.hungerslayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    //Button btnLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword=(MaterialEditText)findViewById(R.id.password);
        edtEmail=(MaterialEditText)findViewById(R.id.email);
        //btnLogIn=(Button)findViewById(R.id.btnLogIn);


    }
    public void btnLogIn(View view){
        String emailID=edtEmail.getText().toString();
        String password=edtPassword.getText().toString();
        String type="SignIn";

        //Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        background bg=new background(this);
        bg.execute(type,emailID,password);

    }
}

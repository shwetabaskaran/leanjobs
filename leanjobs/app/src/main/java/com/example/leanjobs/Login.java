package com.example.leanjobs;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    Button btnadmlogin, btnforgotpassword, btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnsignup = (Button) findViewById(R.id.btnsignup);
        btnadmlogin = (Button) findViewById(R.id.btnadmlogin);
        btnforgotpassword = (Button) findViewById(R.id.btnforgotpassword);
        btnadmlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        AdminLogin.class);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "Admin Login Screen", Toast.LENGTH_LONG)
                        .show();
            }
        });

        btnforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        ForgotPassword.class);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "Forgot password Screen", Toast.LENGTH_LONG)
                        .show();
            }
        });


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent i = new Intent(getApplicationContext(),Signup.class);
                startActivity(i);
                finish();
              */
                Toast.makeText(getApplicationContext(),
                        "Signup Screen", Toast.LENGTH_SHORT)
                        .show();
            }
        });



    }
}

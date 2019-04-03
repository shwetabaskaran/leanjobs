package com.example.leanjobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Toast.makeText(getApplicationContext(),
                "Forgot Password Screen", Toast.LENGTH_SHORT)
                .show();
    }

    public void onBackPressed()
    {

        Intent intent = new Intent(ForgotPassword.this, Login.class);
        startActivity(intent);
        finish();

    }
}


package com.example.leanjobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class User_HomeScreen extends AppCompatActivity {
    String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home_screen);
        Intent intent = getIntent();
        UserId = intent.getStringExtra("userid");
//                Toast.makeText(getApplicationContext(),
//                UserId, Toast.LENGTH_LONG)
//                .show();

        jobs();
        logout();
    }

    private void logout() {
        Button logout = (Button) findViewById(R.id.btnLogout);
        SharedPreferences LoginDetails = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE);
        LoginDetails.edit().clear().commit();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        Login.class);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "User Logged out successfully", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void jobs(){
        Button job = (Button) findViewById(R.id.button3);
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        User_list_of_jobs.class);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "User's list of jobs screen", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
package com.example.leanjobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_HomeScreen extends AppCompatActivity {
    String UserId, FullName, Email, PhoneNo,Salt;
TextView TxtWelcomeAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home_screen);
        Intent intent = getIntent();
        TxtWelcomeAdmin = findViewById(R.id.txtWelcomeAdmin);
        FullName = intent.getStringExtra("FullName");
        TxtWelcomeAdmin.setText("Welcome "+FullName);
        UserId = intent.getStringExtra("userid");
        FullName = intent.getStringExtra("FullName");
        PhoneNo = intent.getStringExtra("Phone_no");
        Salt = intent.getStringExtra("salt");
        Email = intent.getStringExtra("Email");

        Salt = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("salt","");
        jobs();
        profile();
        logout();
    }

    private void logout() {
        Button logout = (Button) findViewById(R.id.btnAdminLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences LoginDetails = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE);
                LoginDetails.edit().clear().commit();
                Intent i = new Intent(getApplicationContext(),
                        Login.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),
                        "Logged out successfully", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


    private void profile() {
        Button job = (Button) findViewById(R.id.btnAdminProfile);
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        UserProfile.class);
                Bundle UserBundle = new Bundle();
                UserBundle.putString("Admin_User_id",UserId);
                UserBundle.putString("Admin_FullName",FullName);
                UserBundle.putString("Admin_PhoneNo",PhoneNo);
                UserBundle.putString("Admin_Salt",Salt);
                UserBundle.putString("Admin_Email",Email);
                i.putExtras(UserBundle);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    public void jobs(){
        Button job = (Button) findViewById(R.id.button3);
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        Admin_list_of_jobs.class);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "Admin's list of jobs screen", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}

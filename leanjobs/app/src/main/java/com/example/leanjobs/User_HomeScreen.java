package com.example.leanjobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class User_HomeScreen extends AppCompatActivity {
    String UserId, FullName, Email, PhoneNo,Salt,UserPicURL,ResumePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home_screen);
        Intent intent = getIntent();
//        UserId = intent.getStringExtra("userid");
//        FullName = intent.getStringExtra("FullName");
//        Email = intent.getStringExtra("email");
//        PhoneNo = intent.getStringExtra("phoneNo");
//        Salt = intent.getStringExtra("salt");
//        UserPicURL = intent.getStringExtra("profilePicURL");
//        ResumePath = intent.getStringExtra("resumePath");
        UserId = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("User_user_id","");
        FullName = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("User_fullname",""); ;
        Email = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("User_email",""); ;
        PhoneNo = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("User_phone",""); ;
        Salt = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("UserSalt",""); ;
        UserPicURL = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("User_profpic",""); ;
        ResumePath = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("User_resume",""); ;

        TextView TxtUserHome = (TextView) findViewById(R.id.TxtUserHomeView);
        TxtUserHome.setText("Bienvenido "+FullName);
        jobs();
        applications();
        logout();
        profile();
    }

    private void profile() {
        Button job = (Button) findViewById(R.id.btnUserProfile);
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        UserProfile.class);
                Bundle UserBundle = new Bundle();
                UserBundle.putString("User_User_id",UserId);
                UserBundle.putString("User_FullName",FullName);
                UserBundle.putString("User_PhoneNo",PhoneNo);
                UserBundle.putString("User_Salt",Salt);
                UserBundle.putString("User_Email",Email);
                //UserBundle.putString("User_ProfileURL",UserPicURL);
                //UserBundle.putString("User_ResumePath",ResumePath);
                i.putExtras(UserBundle);
                startActivity(i);
                finish();
            }
        });
    }

    private void logout() {
        Button logout = (Button) findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences LoginDetails = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE);
                LoginDetails.edit().clear().commit();
                Intent i = new Intent(getApplicationContext(),
                        Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),
                        "Usuario desconectado con éxito", Toast.LENGTH_LONG)
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
                Bundle UserBundle = new Bundle();
                UserBundle.putString("User_User_id",UserId);
                UserBundle.putString("User_FullName",FullName);
                UserBundle.putString("User_PhoneNo",PhoneNo);
                UserBundle.putString("User_Salt",Salt);
                UserBundle.putString("User_Email",Email);
                //UserBundle.putString("User_ProfileURL",UserPicURL);
                //UserBundle.putString("User_ResumePath",ResumePath);
                i.putExtras(UserBundle);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "Pantalla de lista de trabajos del usuario", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void applications(){
        Button job = (Button) findViewById(R.id.button4);
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        ViewMyApplications.class);
                Bundle UserBundle = new Bundle();
                UserBundle.putString("User_User_id",UserId);
                UserBundle.putString("User_FullName",FullName);
                UserBundle.putString("User_PhoneNo",PhoneNo);
                UserBundle.putString("User_Salt",Salt);
                UserBundle.putString("User_Email",Email);
                //UserBundle.putString("User_ProfileURL",UserPicURL);
                //UserBundle.putString("User_ResumePath",ResumePath);
                i.putExtras(UserBundle);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(),
                        "Aplicaciones de usuario", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

}
package com.example.leanjobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class User_HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home_screen);

        jobs();
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
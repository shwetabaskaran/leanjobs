package com.example.leanjobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        LinearLayout btn_aboutus = (LinearLayout) findViewById(R.id.btn_aboutus);

        btn_aboutus.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Settings.this, AboutUs.class);
                    startActivity(intent);
                    finish();
                } catch (Exception ex) {

                }
            }
        });


    }
}

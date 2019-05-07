package com.example.leanjobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Settings extends AppCompatActivity {

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.homescreen, menu);
//        return true;
//        //return super.onCreateOptionsMenu(menu);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.home:
//                Intent i = new Intent(getApplicationContext(),User_HomeScreen.class);
//                startActivity(i);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

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

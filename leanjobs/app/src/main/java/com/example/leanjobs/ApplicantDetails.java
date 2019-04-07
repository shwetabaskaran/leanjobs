package com.example.leanjobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ApplicantDetails extends AppCompatActivity {

    Button btnQuestionResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);
        btnQuestionResponse = (Button) findViewById(R.id.buttonQuestionResponse);
        btnQuestionResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        QuestionnaireResponse.class);
                startActivity(i);
                finish();
//                Toast.makeText(getApplicationContext(),
//                        "Admin Login Screen", Toast.LENGTH_LONG)
//                        .show();
            }
        });;
    }
}

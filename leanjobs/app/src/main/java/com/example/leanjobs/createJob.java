package com.example.leanjobs;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.leanjobs.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class createJob extends AppCompatActivity {
    String JobReq;
    String User_Id;
    String Salt;
    EditText JobDesc;
    EditText WagePerHour;
    EditText JobTitle;
    EditText Question1;
    EditText Question2;
    EditText Question3;
    EditText Question4;
    EditText Question5;
    EditText TxtJobReq;
    Button submit;
    Button cancel;
    String URLPost = "http://dhillonds.com/leanjobsweb/index.php/api/jobs/create";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        JobDesc = findViewById(R.id.JobDesc);
        JobTitle = findViewById(R.id.JobTitle);
        WagePerHour = findViewById(R.id.WagePerHour);
        Question1 = findViewById(R.id.editQuestions1);
        Question2 = findViewById(R.id.editQuestions2);
        Question3 = findViewById(R.id.editQuestions3);
        Question4 = findViewById(R.id.editQuestions4);
        Question5 = findViewById(R.id.editQuestions5);
        TxtJobReq = findViewById(R.id.TxtJobReq);
        submit = findViewById(R.id.submitbutton);
        cancel = findViewById(R.id.cancelbutton);
        User_Id = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("user_id","");
        Salt = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("salt","");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postJobData();
                } catch (Exception ex) {
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cancelJob();

                } catch (Exception ex) {
                }
            }
        });
    }

    private void postJobData(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLPost, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject UserCredentials = new JSONObject(response);
                    Toast.makeText(getApplication(),response.toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), createJob.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception ex){
                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(createJob.this,error+"Error",Toast.LENGTH_SHORT).show();
                    }
                }) {


            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String jobDesc = JobDesc.getText().toString();
                String jobTitle = JobTitle.getText().toString();
                String wagePerHour = WagePerHour.getText().toString();
                String JobReq = TxtJobReq.getText().toString();
                String question1 = Question1.getText().toString().trim();
                String question2 = Question2.getText().toString().trim();
                String question3 = Question3.getText().toString().trim();
                String question4 = Question4.getText().toString().trim();
                String question5 = Question5.getText().toString().trim();
                String[] questions1 = new String[5];
                questions1[0] = question1;
                questions1[1] = question2;
                questions1[2] = question3;
                questions1[3] = question4;
                questions1[4] = question5;
                String[] questions = new String[5];

                for(int i = 0; i < 5; i++){
                    if(questions1[i] != ""){
                        questions[i] = questions1[i];
                    }
                }
                JSONArray QuestionArray = new JSONArray();
                for(int i=0; i < questions.length; i++) {
                    QuestionArray.put(questions[i].trim());   // create array and add items into that
                }
                params.put("questions_data",QuestionArray.toString());
                params.put("title", jobTitle);
                params.put("role_desc", jobDesc);
                params.put("job_reqs",JobReq);
                params.put("wages",wagePerHour);
                params.put("user_id",User_Id);
                params.put("salt", Salt);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    private void cancelJob(){

    }
}
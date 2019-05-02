package com.example.leanjobs;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
        TxtJobReq = findViewById(R.id.JobReq);
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
                    Intent intent = new Intent(getApplicationContext(), Admin_list_of_jobs.class);
                    startActivity(intent);
                    finish();

                } catch (Exception ex) {
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(createJob.this, Admin_list_of_jobs.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void postJobData(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLPost, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject UserCredentials = new JSONObject(response);
                    Toast.makeText(getApplication(),response.toString(),Toast.LENGTH_SHORT).show();

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
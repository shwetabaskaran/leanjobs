package com.example.leanjobs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
    Button btnSave;
    EditText fullName,emailAddress,mobileNumber;
    String URLPost="http://dhillonds.com/leanjobsweb/index.php/api/users/update_profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        btnSave = findViewById(R.id.userprofSave);
        fullName = findViewById(R.id.edituserFullName);
        emailAddress = findViewById(R.id.edituserEmail);
        mobileNumber = findViewById(R.id.edituserMobile);

        fullName.setText(getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("full_name",""));
        mobileNumber.setText(getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("phone_num",""));
        emailAddress.setText(getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("email",""));

        emailAddress.setEnabled(false);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PostSignUpData();
                } catch (Exception ex) {
                }
            }
        });
    }
    private void PostSignUpData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLPost, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).edit() .putString("phone_num", mobileNumber.getText().toString()).commit();
                    getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).edit() .putString("full_name", fullName.getText().toString()).commit();
                    JSONObject UserProfileData = new JSONObject(response);
                    String LoginFlag = UserProfileData.getString("status");
                    String Message = UserProfileData.getString("message");
                    Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
                    if(LoginFlag == "true"){
                        Intent intent = new Intent(getApplicationContext(),
                                UserProfile.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(LoginFlag == "false"){
                        Toast.makeText(getApplication(),Message,Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){

                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserProfile.this,error+"",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                String FullName = fullName.getText().toString();
                String EmailAddress = emailAddress.getText().toString();
                String Mobileno = mobileNumber.getText().toString();
                String Salt = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("salt","");
                String User_Id = getSharedPreferences("UserDataPreferences", Context.MODE_PRIVATE).getString("user_id","");
                params.put("full_name",FullName);
                params.put("phone_num",Mobileno);
                params.put("salt",Salt);
                params.put("user_id",User_Id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

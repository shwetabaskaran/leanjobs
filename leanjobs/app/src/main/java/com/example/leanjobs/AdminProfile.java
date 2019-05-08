package com.example.leanjobs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class AdminProfile extends AppCompatActivity {
    String URLPost="http://dhillonds.com/leanjobsweb/index.php/api/users/update_profile";
    EditText FullName,Email,PhoneNo;
    Button Save;
    String fullName,email,phoneNo,Salt,UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminprofile);
        FullName = findViewById(R.id.editadminFullName);
        Email = findViewById(R.id.editadminEmail);
        PhoneNo = findViewById(R.id.editAdminePhone);
        Email.setEnabled(false);

        fullName =  getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("admin_fullname","");
        email =  getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("admin_email","");
        phoneNo = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("admin_phone","");
        Salt = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("salt","");
        UserId = getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).getString("user_id","");

        FullName.setText(fullName);
        Email.setText(email);
        PhoneNo.setText(phoneNo);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Save = findViewById(R.id.adminprofSave);
        Save.setOnClickListener(new View.OnClickListener() {
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
                    JSONObject UserCredentials = new JSONObject(response);
                    String LoginFlag = UserCredentials.getString("status");
                    String Message = UserCredentials.getString("message");
                    if(LoginFlag == "true"){
                        fullName = FullName.getText().toString();
                        phoneNo = PhoneNo.getText().toString();
                        getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).edit() .putString("admin_fullname", fullName).commit();
                        getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).edit() .putString("admin_phone", phoneNo).commit();
                        Intent i = new Intent(getApplicationContext(),Admin_HomeScreen.class);
                        startActivity(i);
                        finish();
                    }
                    else if(LoginFlag == "false"){
                        Toast.makeText(getApplication(),Message,Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplication(),ex.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminProfile.this,error+"",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("full_name", fullName);
                params.put("phone_num", phoneNo);
                params.put("salt", Salt);
                params.put("user_id", UserId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(AdminProfile.this, Admin_HomeScreen.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

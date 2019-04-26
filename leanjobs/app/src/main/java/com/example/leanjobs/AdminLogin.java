package com.example.leanjobs;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

public class AdminLogin extends AppCompatActivity {
    Button btnLogin;
    String URLPost="http://dhillonds.com/leanjobsweb/index.php/api/users/login";
    EditText Email,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PostSignUpData();
                } catch (Exception ex) {
                }
            }
        });
        try{
            android.support.v7.app.ActionBar actionBar =getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }catch (Exception ex){
            Toast.makeText(getApplication(),ex.toString(),Toast.LENGTH_SHORT).show();
        }

    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Login.class);
        startActivityForResult(myIntent, 0);
        return true;
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
                        JSONObject Data = UserCredentials.getJSONObject("data");
                        String Salt = Data.getString("salt");
                       // getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).edit() .putString("salt", Salt).commit();
                        String User_ID = Data.getString("user_id");
                       // getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).edit() .putString("user_id", User_ID).commit();
                        String Email = Data.getString("email");
                       // getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).edit() .putString("email", Email).commit();
                        String Phone_num = Data.getString("phone_num");
                        //getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).edit() .putString("phone_num", Phone_num).commit();
                        String Full_Name = Data.getString("full_name");
                       //getSharedPreferences("AdminDataPreferences", Context.MODE_PRIVATE).edit() .putString("full_name", Full_Name).commit();
                        Intent intent = new Intent(getApplicationContext(),
                                Admin_HomeScreen.class);
                        intent.putExtra("userid",User_ID);
                        intent.putExtra("FullName",Full_Name);
                        intent.putExtra("Phone_no",Phone_num);
                        intent.putExtra("salt",Salt);
                        intent.putExtra("Email",Email);

                        startActivity(intent);
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
                Toast.makeText(AdminLogin.this,error+"",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                int Is_Admin = 1;
                params.put("email",email);
                params.put("password",password);
                params.put("is_admin",String.valueOf(Is_Admin));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(AdminLogin.this, Login.class);
        startActivity(intent);
        finish();

    }

}